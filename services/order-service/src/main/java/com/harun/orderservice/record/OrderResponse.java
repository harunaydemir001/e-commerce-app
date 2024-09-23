package com.harun.orderservice.record;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.harun.orderservice.enums.PaymentMethod;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record OrderResponse(
    Integer id,
    String reference,
    BigDecimal amount,
    PaymentMethod paymentMethod,
    String customerId
) {

}