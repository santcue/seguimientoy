package cue.edu.co.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SalesDetails {

    private int id_sale;
    private int id_toy;
    private int quantity;
    private double price;

}
