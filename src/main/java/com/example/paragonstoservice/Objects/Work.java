package com.example.paragonstoservice.Objects;

import lombok.Value;

@Value
public class Work {
    private Long id;

    private Long order_id;

    private String description;

    private Double price;
}
