package com.harun.orderservice.record;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
