package cue.edu.co.mapping.dtos;

import lombok.Builder;

@Builder
public record SalesDto (int id, String date, int client_id, int id_employee) {
}
