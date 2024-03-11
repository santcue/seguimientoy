package cue.edu.co.mapping.mappers;

import cue.edu.co.mapping.dtos.SalesDto;
import cue.edu.co.model.Sales;

public class SalesMapper {

    public static SalesDto mapFromModel(Sales sales){
        return new SalesDto(sales.getId(), sales.getDate(), sales.getClient_id(), sales.getId_employee());
    }

    public static Sales mapToModel(SalesDto sales){
        return Sales.builder()
                .id(sales.id())
                .date(sales.date())
                .client_id(sales.client_id())
                .id_employee(sales.id_employee())
                .build();
    }
}
