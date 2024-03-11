package cue.edu.co.mapping.mappers;

import cue.edu.co.mapping.dtos.SalesDetailsDto;
import cue.edu.co.model.SalesDetails;

public class SalesDetailsMapper {

    public static SalesDetailsDto mapToModel(SalesDetails salesDetails){
        return new SalesDetailsDto(salesDetails.getId_sale(), salesDetails.getId_toy(), salesDetails.getQuantity(), salesDetails.getPrice());
    }

    public static SalesDetails mapFromModel(SalesDetailsDto salesDetails){
        return SalesDetails.builder()
                .id_sale(salesDetails.id_sale())
                .id_toy(salesDetails.id_toy())
                .price(salesDetails.price())
                .quantity(salesDetails.quantity())
                .build();
    }

}
