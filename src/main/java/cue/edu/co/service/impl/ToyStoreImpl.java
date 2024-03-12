package cue.edu.co.service.impl;

import cue.edu.co.database.DataBaseConnection;

import cue.edu.co.mapping.dtos.*;
import cue.edu.co.mapping.mappers.*;
import cue.edu.co.model.*;
import cue.edu.co.repository.Repository;
import cue.edu.co.repository.impl.client.ClientJDBCImpl;
import cue.edu.co.repository.impl.employee.EmployeeJDBCImpl;
import cue.edu.co.repository.impl.sales.SalesJDBCImpl;
import cue.edu.co.repository.impl.salesdetails.SalesDetailsJDBCImpl;
import cue.edu.co.service.ToyRepository;
import cue.edu.co.service.ToyStore;
import lombok.Getter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Getter
public class ToyStoreImpl implements ToyStore {

    Connection conn = DataBaseConnection.getInstance();
    private ToyRepository<Toy> toyRepository;
    private Repository<Employee> employeesRepository;
    private Repository<Client> clientsRepository;
    private static Repository<Sales> saleRepository;
    private static Repository<SalesDetails> saleDetailsRepository;

    public ToyStoreImpl() throws SQLException {
        this.toyRepository = new ToyRepositoryJDBCImpl();
        this.employeesRepository = new EmployeeJDBCImpl();
        this.clientsRepository = new ClientJDBCImpl();
        saleDetailsRepository = new SalesDetailsJDBCImpl();
        saleRepository = new SalesJDBCImpl();
    }
    @Override
    public void addToy(ToyDto toyDTO) {
        toyRepository.save(ToyMapper.mapFromDTO(toyDTO));
    }

    @Override
    public void addEmployees(EmployeeDto employeesDTO) {
        employeesRepository.save(EmployeeMapper.mapFromModel(employeesDTO));
    }

    @Override
    public void addCliente(ClientDto clientsDTO) {
        clientsRepository.save(ClientMapper.mapFromModel(clientsDTO));

    }

    @Override
    public void addSale(SalesDto saleDTO) {
        saleRepository.save(SalesMapper.mapToModel(saleDTO));
    }

    @Override
    public void addSaleDetails(SalesDetailsDto saleDetailsDTO) {
        saleDetailsRepository.save(SalesDetailsMapper.mapFromModel(saleDetailsDTO));
    }

    @Override
    public List<ClientDto> listCustomers() {
        return clientsRepository.list()
                .stream()
                .map(ClientMapper::mapFromModel)
                .toList();
    }

    @Override
    public List<SalesDto> listSales() {
        return saleRepository.list()
                .stream()
                .map(SalesMapper::mapFromModel)
                .toList();
    }

    @Override
    public List<EmployeeDto> listEmployees() {
        return employeesRepository.list()
                .stream()
                .map(EmployeeMapper::mapFromModel)
                .toList();
    }

    @Override
    public List<SalesDetailsDto> listSaleDetails() {
        return null;
    }

    @Override
    public List<ToyDto> listToys() {
        return toyRepository.list()
                .stream()
                .map(ToyMapper::mapFromModel)
                .toList();


    }

    @Override
    public List<ToyDto> showByType() {
        return toyRepository.showByTYpe()
                .stream()
                .map(ToyMapper::mapFromModel)
                .toList();
    }


    @Override
    public ToyDto search(Integer id) throws SQLException {
        return ToyMapper.mapFromModel(toyRepository.byId(id));
    }

    @Override
    public int getTotalStock() {
        return toyRepository.getTotalStock();
    }

    @Override
    public double getTotalValue() {
        return toyRepository.getTotalValue();
    }

    @Override
    public String getTypeWithMostToys() {
        return toyRepository.TypeWithMostToys();
    }

    @Override
    public String getTypeWithLeastToys() {
        return toyRepository.TypeWithLeastToys();
    }


    @Override
    public List<ToyDto> getToysWithValueGreaterThan(int value) {
        List<Toy> toys = toyRepository.ToysWithAnValue(value);
        return toys.stream()
                .map(ToyMapper::mapFromModel)
                .toList();
    }

    @Override
    public List<ToyDto> orderByStockQuantity() {
        List<Toy> toys = toyRepository.orderByStockQuantity();
        return toys.stream()
                .map(ToyMapper::mapFromModel)
                .toList();
    }

    @Override
    public void updateStock(int toyId, int quantityChange) {
        toyRepository.updateStock(toyId, quantityChange);
    }

}