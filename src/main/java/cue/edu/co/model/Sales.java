package cue.edu.co.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Sales {

    private int id;
    private String date;
    private int client_id;
    private int id_employee;

}
