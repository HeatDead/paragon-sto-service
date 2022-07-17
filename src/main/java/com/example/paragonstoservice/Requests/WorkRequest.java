package com.example.paragonstoservice.Requests;

import lombok.Data;

import java.util.List;

@Data
public class WorkRequest {
    Long order;

    String description;

    List<Long> used_parts;

    Double work_price;
}