package cue.edu.co.mapping.mappers;

import cue.edu.co.mapping.dtos.ClientDto;
import cue.edu.co.model.Client;

public class ClientMapper {

    public static ClientDto mapFromModel(Client client){
        return new ClientDto(client.getId(), client.getName(), client.getAddress(), client.getPhone(), client.getEmail(), client.getPurchase_history(), client.getGender(), client.getBirthdate(), client.getIdentity());
    }

    public static Client mapFromModel(ClientDto client) {
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

}
