package cue.edu.co.mapping.dtos;

import lombok.Builder;

@Builder
public record SalesDetailsDto (int id_sale, int id_toy, int quantity, double price) {
}
