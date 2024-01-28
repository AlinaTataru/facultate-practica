package com.example.programare_examene.common.exception.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorResponse {
    private Map<String, Object> result;

    public ErrorResponse(String message, int status) {
        result = new HashMap<>(2);

        result.put("statusMessage", message);
        result.put("statusCode", status);
    }
}
