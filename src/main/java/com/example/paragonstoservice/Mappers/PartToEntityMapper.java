package com.example.paragonstoservice.Mappers;

import com.example.paragonstoservice.Entities.PartEntity;
import com.example.paragonstoservice.Objects.Part;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartToEntityMapper {
    PartEntity partToPartEntity(Part part);
    Part partEntityToPart(PartEntity partEntity);
}
