package cue.edu.co.mapping.mappers;

import cue.edu.co.mapping.dtos.ToyDto;
import cue.edu.co.model.Toy;

public class ToyMapper {

    public static ToyDto mapFromModel(Toy toy){
        return new ToyDto(toy.getName(), toy.getType(), toy.getPrice(), toy.getQuantity());
    }

    public static Toy mapFromModel(ToyDto toy){
        return new Toy(toy.name(), toy.type(), toy.price(), toy.quantity());
    }
}
