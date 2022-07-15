package com.example.paragonstoservice.Repositories;

import com.example.paragonstoservice.Entities.WorkEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkRepository extends CrudRepository<WorkEntity, Long> {
    @Query("SELECT c FROM WorkEntity c WHERE c.order_id = :order_id")
    List<WorkEntity> findAllByOrderId(Long order_id);
}
