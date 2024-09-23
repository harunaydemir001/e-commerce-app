package com.harun.orderservice.record;

public record OrderLineResponse(
        Integer id,
        double quantity
) { }