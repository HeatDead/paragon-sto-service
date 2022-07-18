package com.example.paragonstoservice.Repositories;

import com.example.paragonstoservice.Entities.UsedPartsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsedPartsRepository extends CrudRepository<UsedPartsEntity, Long> {
    @Query("SELECT c FROM UsedPartsEntity c WHERE c.work_id = :work_id")
    List<UsedPartsEntity> findAllByWork_id(Long work_id);
}
