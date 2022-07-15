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

    private Long order_id;
    private Long brand_id;
    private Long model_id;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PartTypeEntity typeEntity;

    private boolean used;

    @ManyToOne
    @JoinColumn(name = "work_id")
    private WorkEntity workEntity;
}
