package com.example.paragonstoservice.Mappers;

import com.example.paragonstoservice.Entities.PartTypeEntity;
import com.example.paragonstoservice.Objects.Part;
import com.example.paragonstoservice.Objects.PartType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartTypeToEntityMapper {
    PartTypeEntity partTypeToPartTypeEntity(Part part);
    PartType partTypeEntityToPartType(PartTypeEntity partTypeEntity);
}
