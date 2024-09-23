package com.harun.customerservice.record;

import com.harun.customerservice.entity.Address;

public record CustomerResponse(String id,
                               String firstName,
                               String lastName,
                               String email,
                               Address address) {}
