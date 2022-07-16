package com.example.paragonstoservice.Objects;

import lombok.Value;

@Value
public class Work {
    Long id;

    Long order_id;

    String description;

    Double price;
}