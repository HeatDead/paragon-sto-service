package com.example.paragonstoservice.Requests;

import lombok.Data;

@Data
public class WorkRequest {
    Long order;

    String description;

    Double price;
}