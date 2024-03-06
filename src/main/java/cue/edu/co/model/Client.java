package cue.edu.co.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Client {

    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String purchase_history;
    private String gender;
    private String birthdate;
    private String identity;

}
