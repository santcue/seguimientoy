package cue.edu.co.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Toy {

    private int id;
    private String name;
    private Category type;
    private double price;
    private int quantity;

}
