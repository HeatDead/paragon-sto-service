package com.example.paragonstoservice.Requests;

import lombok.Data;

@Data
public class PartRequest {
    String name;

    Long brand;
    Long model;

    Double price;

    Long type;
}