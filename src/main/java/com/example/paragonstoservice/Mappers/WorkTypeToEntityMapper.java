package com.example.paragonstoservice.Mappers;

import com.example.paragonstoservice.Entities.WorkTypeEntity;
import com.example.paragonstoservice.Objects.WorkType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkTypeToEntityMapper {
    WorkTypeEntity workTypeToWorkTypeEntity(WorkType workType);
    WorkType workTypeEntityToWorkType(WorkTypeEntity workTypeEntity);
}
