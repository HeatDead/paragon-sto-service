package com.example.paragonstoservice.Objects;

import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
public class Work {
    Long id;

    Long order_id;

    String description;

    Double price;
    Double total_price;

    List<UsedPart> used_parts;
}