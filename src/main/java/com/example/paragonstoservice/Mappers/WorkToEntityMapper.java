package com.example.paragonstoservice.Mappers;

import com.example.paragonstoservice.Entities.WorkEntity;
import com.example.paragonstoservice.Objects.Work;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkToEntityMapper {
    WorkEntity workToWorkEntity(Work work);
    Work workEntityToWork(WorkEntity workEntity);
}
