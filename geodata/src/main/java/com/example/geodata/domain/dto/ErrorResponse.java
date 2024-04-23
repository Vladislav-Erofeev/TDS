package com.example.geodata.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ErrorResponse {
    private final String message;
    private final Long timestamp = System.currentTimeMillis();

    public ErrorResponse(String message) {
        this.message = message;
    }
}
