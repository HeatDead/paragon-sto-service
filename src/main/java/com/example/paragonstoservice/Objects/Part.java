package com.example.paragonstoservice.Objects;

import lombok.Value;

@Value
public class Part {
    Long id;

    String name;
    Long brand_id;
    Long model_id;

    Double price;

    PartType type;

    int count;
}