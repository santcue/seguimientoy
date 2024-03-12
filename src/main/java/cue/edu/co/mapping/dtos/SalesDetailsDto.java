package cue.edu.co.mapping.dtos;

import cue.edu.co.model.Sales;
import cue.edu.co.model.Toy;
import lombok.Builder;

@Builder
public record SalesDetailsDto (Sales id_sale, Toy id_toy, int quantity, double price) {
}
