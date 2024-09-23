package com.harun.customerservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {
    String street;
    String houseNumber;
    String zipCode;
}
