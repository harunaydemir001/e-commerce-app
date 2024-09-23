package com.harun.customerservice.handler;


import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
