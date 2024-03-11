package cue.edu.co.service;

import cue.edu.co.mapping.dtos.*;
import cue.edu.co.model.Category;
import cue.edu.co.model.Toy;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ToyStore {


    void addEmployees(EmployeeDto employeeDTO);
    void addCliente(ClientDto clientsDTO);
    void addSale(SalesDto saleDTO);
    void addSaleDetails(SalesDetailsDto saleDetailsDTO);
    List<ClientDto> listCustomers();
    List<SalesDto> listSales();
    List<EmployeeDto> listEmployees();
    List<SalesDetailsDto> listSaleDetails();

    void addToy(ToyDto toy);
    List<ToyDto> countToysByType(Category category) throws Exception;
    ToyDto search(Integer id) throws SQLException;
    int getTotalQuantity();
    double getTotalValue();
    void decreaseStock(Toy toy, int quantity);
    void increaseStock(Toy toy, int quantity);
    void updateStock(int toyId, int quantityChange);
}
