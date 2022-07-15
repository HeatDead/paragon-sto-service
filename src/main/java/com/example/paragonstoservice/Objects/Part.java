package com.example.paragonstoservice.Objects;

import lombok.Value;

@Value
public class Part {
    private Long id;

    private Long order_id;
    private Long brand_id;
    private Long model_id;

    private Double price;

    private PartType type;

    private boolean used;

    private Work work;
}
