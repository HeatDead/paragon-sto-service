package com.example.paragonstoservice.Requests;

import lombok.Data;

@Data
public class PartRequest {
    Long order;

    Long brand;
    Long model;

    Double price;

    Long type;
}