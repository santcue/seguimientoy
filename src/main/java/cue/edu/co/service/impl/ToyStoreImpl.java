package cue.edu.co.service.impl;

import cue.edu.co.database.DataBaseConnection;
import cue.edu.co.mapping.dtos.ToyDto;
import cue.edu.co.mapping.mappers.ToyMapper;
import cue.edu.co.model.*;
import cue.edu.co.repository.Repository;
import cue.edu.co.repository.impl.toy.ToyRepositoryJDBCImpl;
import cue.edu.co.service.ToyRepository;
import cue.edu.co.service.ToyStore;
import lombok.Getter;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class ToyStoreImpl implements ToyStore {

    Connection conn = DataBaseConnection.getInstance();
    private ToyRepository<Toy> toyRepository;
    private Repository<Employee> employeesRepository;
    private Repository<Client> clientsRepository;
    private static Repository<Sales> saleRepository;
    private static Repository<SalesDetails> saleDetailsRepository;

    public ToyStoreImpl() {
        this.toyRepository = new ToyRepositoryJDBClmpl();
        this.employeesRepository = new EmployeRepositoryJDBCimpl();
        this.clientsRepository = new CustomerRepositoryJDBCimpl();
        saleDetailsRepository = new SaleDetailsRepositoryJDBCimpl();
        saleRepository = new SaleRepositoryJDBCimpl();
    }
    @Override
    public void addToy(ToyDto toy) {
        repository.addToy(ToyMapper.mapFromDTO(toy));
    }


    public List<ToyDto> countToysByType(Category type) {
        return inventory.stream()
                .filter(e -> e.type() == type)
                .collect(Collectors.toList());
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        return totalQuantity;
    }

    public double getTotalValue() {
        double totalValue = 0;
        for (Toy toy : inventory) {
            totalValue += toy.getPrice() * toy.getQuantity();
        }
        return totalValue;
    }

    public void decreaseStock(Toy toy, int quantity) {
        int currentQuantity = toy.getQuantity();
        if (quantity <= currentQuantity) {
            toy.setQuantity(currentQuantity - quantity);
        } else {
            throw new IllegalArgumentException("Not enough stock to decrease.");
        }
    }

    public void increaseStock(Toy toy, int quantity) {
        toy.setQuantity(toy.getQuantity() + quantity);
    }

    public char getTypeWithMostToys() {
        Map<Character, Integer> counter = countToysByType();
        char typeMostToys = ' ';
        int maxQuantity = Integer.MIN_VALUE;
        for (Map.Entry<Character, Integer> entry : counter.entrySet()) {
            if (entry.getValue() > maxQuantity) {
                maxQuantity = entry.getValue();
                typeMostToys = entry.getKey();
            }
        }
        return typeMostToys;
    }

    public char getTypeWithLeastToys() {
        Map<Character, Integer> counter = countToysByType();
        char typeLeastToys = ' ';
        int minQuantity = Integer.MAX_VALUE;
        for (Map.Entry<Character, Integer> entry : counter.entrySet()) {
            if (entry.getValue() < minQuantity) {
                minQuantity = entry.getValue();
                typeLeastToys = entry.getKey();
            }
        }
        return typeLeastToys;
    }

    public List<Toy> getToysWithValueGreaterThan(double value) {
        List<Toy> toysWithValueGreaterThan = new ArrayList<>();
        for (Toy toy : inventory) {
            if (toy.getPrice() > value) {
                toysWithValueGreaterThan.add(toy);
            }
        }
        return toysWithValueGreaterThan;
    }
}