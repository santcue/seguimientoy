package cue.edu.co.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SalesDetails {

    private Sales id_sale;
    private Toy id_toy;
    private int quantity;
    private double price;

}
