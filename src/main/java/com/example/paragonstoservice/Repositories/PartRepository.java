package com.example.paragonstoservice.Repositories;

import com.example.paragonstoservice.Entities.PartEntity;
import com.example.paragonstoservice.Entities.PartTypeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PartRepository extends CrudRepository<PartEntity, Long> {
    List<PartEntity> findByTypeEntity(PartTypeEntity partTypeEntity);
}
