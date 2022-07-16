package com.example.paragonstoservice.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parts")
public class PartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Long brand_id;
    private Long model_id;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PartTypeEntity typeEntity;

    private int count;
}