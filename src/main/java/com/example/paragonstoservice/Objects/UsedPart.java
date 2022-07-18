package com.example.paragonstoservice.Objects;

import lombok.Data;

@Data
public class UsedPart {
    Long part_id;
    int count;

    String name;

    Long brand_id;
    Long model_id;

    Double price;
}
