package cue.edu.co.mapping.dtos;

import lombok.Builder;

@Builder
public record ClientDTO(int id, String name, String address, String phone, String email,
                        String purchase_history, String gender, String birthdate, String identity) {
}
