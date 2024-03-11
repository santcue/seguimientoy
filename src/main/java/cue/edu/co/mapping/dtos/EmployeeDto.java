package cue.edu.co.mapping.dtos;

import lombok.Builder;

@Builder
public record EmployeeDto(int id, String name, String address, String phone, String email,
                          String post, double salary, String employment_history, String password, String birthdate) {
}
