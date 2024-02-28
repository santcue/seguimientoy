package cue.edu.co.service;

import cue.edu.co.mapping.dtos.ToyDto;
import cue.edu.co.model.Toy;

import java.util.List;
import java.util.Map;

public interface ToyStore {
    List<Toy> addToy(ToyDto toy);
    List<Toy> getInventory();
    Map<Character, Integer> countToysByType();
    int getTotalQuantity();
    double getTotalValue();
    void decreaseStock(Toy toy, int quantity);
    void increaseStock(Toy toy, int quantity);
}
