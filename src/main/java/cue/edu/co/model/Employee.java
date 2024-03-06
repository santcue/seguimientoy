package cue.edu.co.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Employee {

    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String post;
    private double salary;
    private String employment_history;
    private String password;
    private String birthdate;

}
