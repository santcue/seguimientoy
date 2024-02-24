package cue.edu.co.service.impl;

import cue.edu.co.mapping.dtos.ToyDto;
import cue.edu.co.mapping.mappers.ToyMapper;
import cue.edu.co.model.Toy;
import cue.edu.co.service.ToyStore;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToyStoreImpl implements ToyStore {
    private List<Toy> inventory;
    private static final String DATA_FILE = "data.dat";

    public ToyStoreImpl() {
        this.inventory = new ArrayList<>();
    }

    public List<Toy> addToy(ToyDto toy) {
        inventory.add(ToyMapper.mapFromModel(toy));
        saveData();
        return inventory;
    }

    public List<Toy> getInventory() {
        return inventory;
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(inventory);
            System.out.println("Data saved successfully in " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    public static ToyStoreImpl loadStore() {
        ToyStoreImpl store = new ToyStoreImpl();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                store.inventory = (List<Toy>) obj;
                System.out.println("Data loaded successfully from " + DATA_FILE);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Data file not found. Creating new store.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
        return store;
    }

    public Map<Character, Integer> countToysByType() {
        Map<Character, Integer> counter = new HashMap<>();
        for (Toy toy : inventory) {
            char type = toy.getType();
            counter.put(type, counter.getOrDefault(type, 0) + 1);
        }
        return counter;
    }

    public int getTotalQuantity() {
        int total = 0;
        for (Toy toy : inventory) {
            total += toy.getQuantity();
        }
        return total;
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
        saveData();
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
