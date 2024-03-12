package cue.edu.co.service;

import cue.edu.co.mapping.dtos.*;
import cue.edu.co.model.Category;
import cue.edu.co.model.Toy;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ToyStore {


    void addToy(ToyDto toyDTO);
    void addEmployees(EmployeeDto employeesDTO);
    void addCliente(ClientDto clientsDTO);
    void addSale(SalesDto saleDTO);
    void addSaleDetails(SalesDetailsDto saleDetailsDTO);
    List<ClientDto> listCustomers();
    List<SalesDto> listSales();
    List<EmployeeDto> listEmployees();
    List<SalesDetailsDto> listSaleDetails();

    List<ToyDto> listToys();
    List<ToyDto> showByType();
    ToyDto search(Integer id) throws SQLException;

    int getTotalStock();

    double getTotalValue();

    String getTypeWithMostToys();

    String getTypeWithLeastToys();



    List<ToyDto> getToysWithValueGreaterThan(int value);

    List<ToyDto> orderByStockQuantity();

    void updateStock(int toyId, int quantityChange);

}
