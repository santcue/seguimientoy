package cue.edu.co.service;

import cue.edu.co.model.Toy;

import java.util.List;

public interface ToyRepository<T> {

    List<T> list();
    T byId(int id);
    int getTotalStock();
    int getTotalValue();
    String TypeWithMostToys();
    String TypeWithLeastToys();
    public List<Toy> ToysWithAnValue(int value);
    public List<Toy> orderByStockQuantity();

    void updateStock(int toyId, int quantityChange);

    void save(Toy toy);

    void delete(int id);

    List<T>showByTYpe();


    void update(Toy toy);

}
