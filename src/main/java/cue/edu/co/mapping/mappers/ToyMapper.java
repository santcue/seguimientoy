package cue.edu.co.mapping.mappers;

import cue.edu.co.mapping.dtos.ClientDTO;
import cue.edu.co.mapping.dtos.EmployeeDTO;
import cue.edu.co.mapping.dtos.ToyDto;
import cue.edu.co.model.Client;
import cue.edu.co.model.Employee;
import cue.edu.co.model.Toy;

public class ToyMapper {

    public static ToyDto mapFromModel(Toy toy){
        return new ToyDto(toy.getName(), toy.getType(), toy.getPrice(), toy.getQuantity());
    }

    public static Toy mapFromModel(ToyDto toy){
        return new Toy(toy.name(), toy.type(), toy.price(), toy.quantity());
    }

    public static Client mapFromModel(ClientDTO client) {
        return Client.builder()
                .name(client.name())
                .phone(client.phone())
                .email(client.email())
                .purchase_history(client.purchase_history())
                .address(client.address())
                .gender(client.gender())
                .birthdate(client.birthdate())
                .identity(client.identity())
                .build();
    }

    public static Employee mapFromModel(EmployeeDTO employee) {
        return Employee.builder()
                .name(employee.name())
                .phone(employee.phone())
                .email(employee.email())
                .address(employee.address())
                .post(employee.post())
                .salary(employee.salary())
                .employment_history(employee.employment_history())
                .password(employee.password())
                .birthdate(employee.birthdate())
                .build();
    }

}
