package cue.edu.co.mapping.dtos;

import lombok.Builder;

@Builder
public record ToyDto(String name, char type, double price, int quantity) {
}
