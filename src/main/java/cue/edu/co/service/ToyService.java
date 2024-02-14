package cue.edu.co.service;

import cue.edu.co.mapping.dtos.ToyDto;
import cue.edu.co.model.Toy;

import java.util.List;

public interface ToyService {
   List<ToyDto> addToy(ToyDto toy);
   List<ToyDto> showQuantityByType(ToyDto toy);
   List<ToyDto> showTotalQuantity(ToyDto toy);
   List<ToyDto> showTotalValue(ToyDto toy);
    List<ToyDto> decreaseStock(ToyDto toy);
    List<ToyDto> increaseStock(ToyDto toy);
    List<ToyDto> showTypeWithMostToys(ToyDto toy);
    List<ToyDto> showTypeWithLeastToys(ToyDto toy);
    List<ToyDto> findToy(ToyDto toy);
    List<ToyDto> getToysWithGreaterValue(ToyDto toy);
    List<ToyDto> sortStockByType(ToyDto toy);

}
