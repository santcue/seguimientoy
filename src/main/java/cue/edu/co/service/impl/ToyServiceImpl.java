package cue.edu.co.service.impl;

import cue.edu.co.mapping.dtos.ToyDto;
import cue.edu.co.mapping.mappers.ToyMapper;
import cue.edu.co.service.ToyService;
import cue.edu.co.model.Toy;
import cue.edu.co.service.ToyStoreImpl;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class ToyServiceImpl implements ToyService {


    private List<Toy> toys;
    private ToyStoreImpl toyStore;

    public ToyServiceImpl() {
        toyStore = ToyStoreImpl.loadStore();
        toys = toyStore.getInventory();

    }

    @Override
    public List<ToyDto> addToy(ToyDto toy) {
        toys.add(ToyMapper.mapFromModel(toy));
        return toys.stream().map(ToyMapper::mapFromModel).toList();
    }

    @Override
    public List<ToyDto> showQuantityByType(ToyDto toy) {
        toys.show(ToyMapper.mapFromModel(toy));
        Map<Character, Integer> quantityByType = toyStore.countToysByType();
        StringBuilder message = new StringBuilder("Quantity of toys by type:\n");
        for (Map.Entry<Character, Integer> entry : quantityByType.entrySet()) {
            message.append("Type ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        JOptionPane.showMessageDialog(null, message.toString());
        return toys.stream().map(ToyMapper::mapFromModel).toList();
    }

    @Override
    public List<ToyDto> showTotalQuantity(ToyDto toy) {
        return null;
    }

    @Override
    public List<ToyDto> showTotalValue(ToyDto toy) {
        return null;
    }

    @Override
    public List<ToyDto> decreaseStock(ToyDto toy) {
        return null;
    }

    @Override
    public List<ToyDto> increaseStock(ToyDto toy) {
        return null;
    }

    @Override
    public List<ToyDto> showTypeWithMostToys(ToyDto toy) {
        return null;
    }

    @Override
    public List<ToyDto> showTypeWithLeastToys(ToyDto toy) {
        return null;
    }

    @Override
    public List<ToyDto> findToy(ToyDto toy) {
        return null;
    }

    @Override
    public List<ToyDto> getToysWithGreaterValue(ToyDto toy) {
        return null;
    }

    @Override
    public List<ToyDto> sortStockByType(ToyDto toy) {
        return null;
    }
}

