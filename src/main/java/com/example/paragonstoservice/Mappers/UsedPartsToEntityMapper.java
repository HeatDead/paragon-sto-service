package com.example.paragonstoservice.Mappers;

import com.example.paragonstoservice.Entities.UsedPartsEntity;
import com.example.paragonstoservice.Objects.UsedPart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsedPartsToEntityMapper {
    UsedPartsEntity usedPartsToUsedPartsEntity(UsedPart usedPart);
    UsedPart usedPartsEntityToUsedParts(UsedPartsEntity usedPartsEntity);
}
