package cue.edu.co.view;

import cue.edu.co.Exceptions.ToyStoreException;
import cue.edu.co.database.DataBaseConnection;
import cue.edu.co.mapping.dtos.*;
import cue.edu.co.model.Category;
import cue.edu.co.model.Toy;

import cue.edu.co.service.impl.ToyStoreImpl;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    private static ToyStoreImpl toyStore;

    public static void main(String[] args) {
        try (Connection conn = DataBaseConnection.getInstance()){
            toyStore = new ToyStoreImpl();
            Scanner scanner = new Scanner(System.in);
            int choice = -7;

            while (choice != 0) {
                System.out.println("Welcome to the toy store");
                System.out.println("1. Show toy list");
                System.out.println("2. Search toy by ID");
                System.out.println("3. Add new toy");
                System.out.println("4. Update stock of a toy");
                System.out.println("5. Get stock total");
                System.out.println("6. Get total inventory value");
                System.out.println("7. Get guy with more toys");
                System.out.println("8. Get type with fewer toys");
                System.out.println("9. Get toys with a value greater than a certain amount");
                System.out.println("10. Sort toys by stock quantity");
                System.out.println("11. Show sales list and its details");
                System.out.println("12. Show employees");
                System.out.println("13. Show Active Customers");
                System.out.println("14. Show Recorded Sales");
                System.out.println("15. New sale");
                System.out.println("16. New Employee");
                System.out.println("17. New client");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");

                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 1:
                            CompletableFuture<List<ToyDto>> future = CompletableFuture.supplyAsync(() -> {
                                List<ToyDto> list = toyStore.listToys();
                                if (!list.isEmpty()) {
                                    for (ToyDto toys : list) {
                                        System.out.println(toys);
                                        System.out.println("Loading...");
                                        try {
                                            Thread.sleep(3000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    System.out.println("There are no toys on the list");
                                }
                                return list;
                            });
                            future.join();
                            System.out.println("The task is completed");
                            break;
                        case 2:
                            searchToy();
                            break;
                        case 3:
                            addNewToy(scanner);
                            break;
                        case 4:
                            updateStock(scanner);
                            break;
                        case 5:
                            getTotalStock();
                            break;
                        case 6:
                            getTotalValue();
                            break;
                        case 7:
                            getTypeWithMostToys();
                            break;
                        case 8:
                            getTypeWithLeastToys();
                            break;
                        case 9:
                            getToysWithValueGreaterThan(scanner);
                            break;
                        case 10:
                            orderByStockQuantity();
                            break;
                        case 11:
                            listSaleDetails();
                            break;
                        case 12:
                            listEmployees();
                            break;
                        case 13:
                            listCustomers();
                            break;
                        case 14:
                            listSales();
                            break;
                        case 15:
                            newSale(scanner);
                            break;
                        case 16:
                            addEmployee(scanner);
                            break;
                        case 17:
                            addCustomer(scanner);
                            break;
                        case 0:
                            System.out.println("Thank you for visiting the toy store. See you later!");
                            break;
                        default:
                            System.out.println("Invalid option. Try again.");
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number.");
                    scanner.nextLine();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ToyStoreException.ToyNotFoundException e) {
                    System.out.println("Error: Toy not found - " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchToy() throws SQLException, ToyStoreException.ToyNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the toy to search: ");
        int id = scanner.nextInt();
        ToyDto toy = toyStore.search(id);
        if (toy != null) {
            System.out.println("Toy found:");
            System.out.println(toy);
        } else {
            System.out.println("No toy found with the provided ID.");
        }
    }

    private static void addNewToy(Scanner scanner) throws SQLException {
        System.out.print("Enter the name of the toy: ");
        String name = scanner.nextLine();

        System.out.print("Enter the price of the toy: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter the quantity in stock of the toy: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the category ID of the toy: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the type of the toy category: ");
        String categoryType = scanner.nextLine();

        Category category = new Category(categoryId, categoryType);

        ToyDto toyDTO = new ToyDto(name, category, price, quantity);

        System.out.println("Toy added successfully.");
    }

    private static void addCustomer(Scanner scanner) throws SQLException {
        System.out.println("Enter the name of the customer");
        String name = scanner.nextLine();

        System.out.println("Enter the customer's identification number");
        String identity = scanner.nextLine();

        System.out.println("Enter the customer's birthdate (in YYYY-MM-DD format)");
        String birthdateStr = scanner.nextLine();

        Date birthdate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            birthdate = dateFormat.parse(birthdateStr);
        } catch (ParseException e) {
            System.out.println("Error parsing birthdate: " + e.getMessage());
            return;
        }

        ClientDto clienteDTO = ClientDto.builder()
                .name(name)
                .identity(identity)
                .birthdate(String.valueOf(birthdate))
                .build();

        System.out.println("Customer added successfully.");
    }

    private static void addEmployee(Scanner scanner) throws SQLException {
        System.out.println("Enter your username");
        String name = scanner.nextLine();

        System.out.println("Enter your password");
        String password = scanner.nextLine();

        System.out.println("Enter your address");
        String address = scanner.nextLine();

        System.out.println("Enter your phone number");
        String phone = scanner.nextLine();

        System.out.println("Enter your email");
        String email = scanner.nextLine();

        System.out.println("Enter your position");
        String post = scanner.nextLine();

        System.out.println("Enter your salary");
        double salary = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter your employment history");
        String employmentHistory = scanner.nextLine();

        System.out.println("Enter your birthdate (in YYYY-MM-DD format)");
        String birthdateStr = scanner.nextLine();

        Date birthdate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            birthdate = dateFormat.parse(birthdateStr);
        } catch (ParseException e) {
            System.out.println("Error parsing birthdate: " + e.getMessage());
            return;
        }
        EmployeeDto employeeDTO = EmployeeDto.builder()
                .name(name)
                .address(address)
                .phone(phone)
                .email(email)
                .post(post)
                .salary(salary)
                .employment_history(employmentHistory)
                .password(password)
                .birthdate(birthdateStr)
                .build();

        System.out.println("Employee added successfully.");
    }

    private static void newSale(Scanner scanner) throws SQLException {
        System.out.println("Enter the client ID");
        int clientId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the employee ID");
        int employeeId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the sale date (in YYYY-MM-DD format)");
        String saleDateStr = scanner.nextLine();
        Date saleDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            saleDate = dateFormat.parse(saleDateStr);
        } catch (ParseException e) {
            System.out.println("Error parsing sale date: " + e.getMessage());
            return;
        }

        System.out.println("Enter the toy ID");
        int toyId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the quantity of toys sold");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        ClientDto clientDto = ClientDto.builder()
                .id(clientId)
                .build();

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(employeeId)
                .build();
        SalesDetailsDto salesDetailsDto = SalesDetailsDto.builder()
                .id_toy(Toy.builder().id(toyId).build())
                .quantity(quantity)
                .build();

        SalesDto salesDto = SalesDto.builder()
                .id(Integer.parseInt(saleDateStr))
                .date(saleDateStr)
                .client_id(clientId)
                .id_employee(employeeId)
                .build();

    }

    private static void updateStock(Scanner scanner) {
        System.out.print("Enter the toy ID to update the stock: ");
        int toyId = scanner.nextInt();
        System.out.print("Enter the stock change amount (positive to add, negative to subtract): ");
        int quantityChange = scanner.nextInt();
        toyStore.updateStock(toyId, quantityChange);
        System.out.println("Stock updated successfully.");
    }

    private static void getTotalStock() {
        int totalStock = toyStore.getTotalStock();
        System.out.println("The total stock in the store is: " + totalStock);
    }

    private static void getTotalValue() {
        double totalValue = toyStore.getTotalValue();
        System.out.println("The total value of inventory in the store is: " + totalValue);
    }

    private static void getTypeWithMostToys() {
        String typeWithMostToys = toyStore.getTypeWithMostToys();
        System.out.println("The type with the most toys is: " + typeWithMostToys);
    }

    private static void getTypeWithLeastToys() {
        String typeWithLeastToys = toyStore.getTypeWithLeastToys();
        System.out.println("The type with the least toys is: " + typeWithLeastToys);
    }

    private static void getToysWithValueGreaterThan(Scanner scanner) {
        System.out.print("Enter the minimum value for the toys you want to search: ");
        int value = scanner.nextInt();
        List<ToyDto> toys = toyStore.getToysWithValueGreaterThan(value);
        displayToys(toys);
    }

    private static void orderByStockQuantity() {
        List<ToyDto> toys = toyStore.orderByStockQuantity();
        displayToys(toys);
    }
    private static void listEmployees() {
        List<EmployeeDto> employees = toyStore.listEmployees();
        displayEmployees(employees);
    }
    private static void listSaleDetails() {
        List<SalesDetailsDto> saleDetails = toyStore.listSaleDetails();
        displaySaleDetails(saleDetails);
    }
    private static void listSales() {
        List<SalesDto> sales = toyStore.listSales();
        displaySales(sales);
    }
    private static void listCustomers(){
        List<ClientDto> clients = toyStore.listCustomers();
        displayClients(clients);
    }



    private static void displayToys(List<ToyDto> toys) {
        if (toys.isEmpty()) {
            System.out.println("No toys available in the store.");
        } else {
            System.out.println("List of toys:");
            for (ToyDto toy : toys) {
                System.out.println(toy);
            }
        }
    }
    private static void displayEmployees(List<EmployeeDto> employees) {
        if (employees.isEmpty()) {
            System.out.println("No employees available.");
        } else {
            System.out.println("List of employees:");
            for (EmployeeDto employ : employees) {
                System.out.println(employ);
            }
        }
    }
    private static void displaySaleDetails(List<SalesDetailsDto> saleDetails) {
        if (saleDetails.isEmpty()) {
            System.out.println("No sales and details recorded.");
        } else {
            System.out.println("List of sales and their details:");
            for (SalesDetailsDto saleDetailsDTO : saleDetails) {
                System.out.println(saleDetailsDTO);
            }
        }
    }
    private static  void displaySales(List<SalesDto> sales){
        if (sales.isEmpty()){
            System.out.println("No sales recorded.");
        } else {
            System.out.println("List of sales");
            for (SalesDto sale : sales) {
                System.out.println(sale);
            }
        }
    }
    private static void displayClients(List<ClientDto> clients){
        if (clients.isEmpty()){
            System.out.println("No clients registered.");
        } else {
            System.out.println("List of clients");
            for (ClientDto client : clients) {
                System.out.println(client);
            }
        }

    }
}
