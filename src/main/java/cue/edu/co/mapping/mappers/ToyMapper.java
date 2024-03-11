package cue.edu.co.mapping.mappers;

import cue.edu.co.mapping.dtos.ToyDto;
import cue.edu.co.model.Toy;

public class ToyMapper {

    public static ToyDto mapFromModel(Toy toy){
        return new ToyDto(toy.getId(), toy.getName(), toy.getType(), toy.getPrice(), toy.getQuantity());
    }

    public static Toy mapFromDTO(ToyDto toy){
        return Toy.builder()
                .id(toy.id())
                .name(toy.name())
                .type(toy.type())
                .price(toy.price())
                .quantity(toy.quantity())
                .build();
    }




}
