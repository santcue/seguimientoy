package cue.edu.co.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Category {
    private int id;
    private String type;
}
