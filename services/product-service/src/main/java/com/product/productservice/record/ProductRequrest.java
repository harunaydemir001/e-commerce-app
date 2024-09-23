package com.product.productservice.record;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequrest(
        Integer id,
        @NotNull(message = "Product name is required")
        String name,
        @NotNull(message = "Product description is required")
        String description,
        @Positive(message = "Available quantity should br positive")
        double availableQuantity,
        @Positive(message = "Price should br positive")
        BigDecimal price,
        @NotNull(message = "Price category is required")
        Integer categoryId
) {
}
