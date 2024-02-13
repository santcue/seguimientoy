package cue.edu.co.view;

import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ToyStore toyStore = ToyStore.loadStore();

        boolean continueExecution = true;
        while (continueExecution) {
            String option = JOptionPane.showInputDialog(
                    "Select an option:\n" +
                            "1. Add toy\n" +
                            "2. Show quantity of toys by type\n" +
                            "3. Show total quantity of toys\n" +
                            "4. Show total value of toys\n" +
                            "5. Decrease stock of a toy\n" +
                            "6. Increase stock of a toy\n" +
                            "7. Show type with most toys\n" +
                            "8. Show type with least toys\n" +
                            "9. Get toys with value greater than\n" +
                            "10. Sort stock by type\n" +
                            "11. Exit"
            );

            switch (option) {
                case "1":
                    addToy(toyStore);
                    break;
                case "2":
                    showQuantityByType(toyStore);
                    break;
                case "3":
                    showTotalQuantity(toyStore);
                    break;
                case "4":
                    showTotalValue(toyStore);
                    break;
                case "5":
                    decreaseStock(toyStore);
                    break;
                case "6":
                    increaseStock(toyStore);
                    break;
                case "7":
                    showTypeWithMostToys(toyStore);
                    break;
                case "8":
                    showTypeWithLeastToys(toyStore);
                    break;
                case "9":
                    getToysWithValueGreaterThan(toyStore);
                    break;
                case "10":
                    sortStockByType(toyStore);
                    break;
                case "11":
                    continueExecution = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option");
            }
        }
    }

    private static void addToy(ToyStore toyStore) {
        String name = JOptionPane.showInputDialog("Enter the name of the toy:");
        char type = JOptionPane.showInputDialog("Enter the type of the toy (O, 1, 2):").charAt(0);
        double price = Double.parseDouble(JOptionPane.showInputDialog("Enter the price of the toy:"));
        int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter the quantity of the toy:"));
        Toy toy = new Toy(name, type, price, quantity);
        toyStore.addToy(toy);
    }

    private static void showQuantityByType(ToyStore toyStore) {
        Map<Character, Integer> quantityByType = toyStore.countToysByType();
        StringBuilder message = new StringBuilder("Quantity of toys by type:\n");
        for (Map.Entry<Character, Integer> entry : quantityByType.entrySet()) {
            message.append("Type ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        JOptionPane.showMessageDialog(null, message.toString());
    }

    private static void showTotalQuantity(ToyStore toyStore) {
        int totalQuantity = toyStore.getTotalQuantity();
        JOptionPane.showMessageDialog(null, "Total quantity of toys: " + totalQuantity);
    }

    private static void showTotalValue(ToyStore toyStore) {
        double totalValue = toyStore.getTotalValue();
        JOptionPane.showMessageDialog(null, "Total value of toys: $" + totalValue);
    }

    private static void decreaseStock(ToyStore toyStore) {
        String toyName = JOptionPane.showInputDialog("Enter the name of the toy to decrease stock:");
        Toy toy = findToy(toyStore, toyName);
        if (toy != null) {
            int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter the quantity to decrease:"));
            toyStore.decreaseStock(toy, quantity);
            JOptionPane.showMessageDialog(null, "Stock decreased successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "The toy is not found in the store.");
        }
    }

    private static void increaseStock(ToyStore toyStore) {
        String toyName = JOptionPane.showInputDialog("Enter the name of the toy to increase stock:");
        Toy toy = findToy(toyStore, toyName);
        if (toy != null) {
            int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter the quantity to increase:"));
            toyStore.increaseStock(toy, quantity);
            JOptionPane.showMessageDialog(null, "Stock increased successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "The toy is not found in the store.");
        }
    }

    private static Toy findToy(ToyStore toyStore, String name) {
        for (Toy toy : toyStore.getInventory()) {
            if (toy.getName().equalsIgnoreCase(name)) {
                return toy;
            }
        }
        return null;
    }

    private static void showTypeWithMostToys(ToyStore toyStore) {
        char typeWithMostToys = toyStore.getTypeWithMostToys();
        JOptionPane.showMessageDialog(null, "Type with most toys: " + typeWithMostToys);
    }

    private static void showTypeWithLeastToys(ToyStore toyStore) {
        char typeWithLeastToys = toyStore.getTypeWithLeastToys();
        JOptionPane.showMessageDialog(null, "Type with least toys: " + typeWithLeastToys);
    }

    private static void getToysWithValueGreaterThan(ToyStore toyStore) {
        double value = Double.parseDouble(JOptionPane.showInputDialog("Enter the minimum value:"));
        List<Toy> toysWithValueGreaterThan = toyStore.getToysWithValueGreaterThan(value);
        StringBuilder message = new StringBuilder("Toys with value greater than $" + value + ":\n");
        for (Toy toy : toysWithValueGreaterThan) {
            message.append(toy.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, message.toString());
    }

    private static void sortStockByType(ToyStore toyStore) {
        List<Toy> sortedInventory = toyStore.getInventory();
        Collections.sort(sortedInventory, (t1, t2) -> {
            if (t1.getType() != t2.getType()) {
                return t1.getType() - t2.getType();
            }
            return t1.getQuantity() - t2.getQuantity();
        });

        StringBuilder message = new StringBuilder("Inventory sorted by type and quantity:\n");
        for (Toy toy : sortedInventory) {
            message.append(toy.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, message.toString());
    }
}
