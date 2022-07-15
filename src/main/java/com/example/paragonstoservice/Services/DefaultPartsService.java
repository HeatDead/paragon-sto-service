package com.example.paragonstoservice.Services;

import com.example.paragonstoservice.Entities.PartEntity;
import com.example.paragonstoservice.Entities.PartTypeEntity;
import com.example.paragonstoservice.Mappers.PartToEntityMapper;
import com.example.paragonstoservice.Mappers.PartTypeToEntityMapper;
import com.example.paragonstoservice.Objects.Part;
import com.example.paragonstoservice.Objects.PartType;
import com.example.paragonstoservice.Repositories.PartRepository;
import com.example.paragonstoservice.Repositories.PartTypeRepository;
import com.example.paragonstoservice.Requests.PartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultPartsService implements PartsService{
    private final PartTypeRepository partTypeRepository;
    private final PartRepository partRepository;

    private final PartTypeToEntityMapper partTypeToEntityMapper;
    private final PartToEntityMapper partToEntityMapper;

    @Override
    public void addPartType(String name) {
        if (name == null)
            return;//ex
        PartTypeEntity entity = new PartTypeEntity();

        entity.setName(name);

        partTypeRepository.save(entity);
    }

    @Override
    public void addPart(PartRequest request) {
        if (request == null)
            return;//ex
        PartEntity entity = new PartEntity();

        entity.setOrder_id(request.getOrder());
        entity.setBrand_id(request.getBrand());
        entity.setModel_id(request.getModel());
        entity.setTypeEntity(partTypeRepository.findById(request.getType()).get());
        entity.setUsed(false);

        partRepository.save(entity);
    }

    @Override
    public List<PartType> getAllPartsTypes() {
        Iterable<PartTypeEntity> iterable = partTypeRepository.findAll();

        List<PartType> partTypes = new ArrayList<>();
        for (PartTypeEntity entity : iterable)
            partTypes.add(partTypeToEntityMapper.partTypeEntityToPartType(entity));

        return partTypes;
    }

    @Override
    public List<Part> getAllParts() {
        Iterable<PartEntity> iterable = partRepository.findAll();

        List<Part> parts = new ArrayList<>();
        for (PartEntity entity : iterable)
            parts.add(partToEntityMapper.partEntityToPart(entity));

        return parts;
    }
}
