package cue.edu.co.mapping.dtos;

import cue.edu.co.model.Category;
import lombok.Builder;

@Builder
public record ToyDto(String name, Category type, double price, int quantity) {
}
