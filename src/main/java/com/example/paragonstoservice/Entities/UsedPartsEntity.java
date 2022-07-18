package com.example.paragonstoservice.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "used_parts")
public class UsedPartsEntity extends Object {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long work_id;
    private Long part_id;
    private int count;

    private String name;

    private Long brand_id;
    private Long model_id;

    private Double price;
}
