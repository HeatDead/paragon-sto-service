package com.example.paragonstoservice.Services;

import com.example.paragonstoservice.Entities.PartEntity;
import com.example.paragonstoservice.Entities.PartTypeEntity;
import com.example.paragonstoservice.Exceptions.ObjectNotFoundException;
import com.example.paragonstoservice.Mappers.PartToEntityMapper;
import com.example.paragonstoservice.Mappers.PartTypeToEntityMapper;
import com.example.paragonstoservice.Objects.Part;
import com.example.paragonstoservice.Objects.PartType;
import com.example.paragonstoservice.Repositories.PartRepository;
import com.example.paragonstoservice.Repositories.PartTypeRepository;
import com.example.paragonstoservice.Requests.OrderPartRequest;
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
        if (name.equals(""))
            throw new IllegalArgumentException("Incorrect name");

        PartTypeEntity entity = new PartTypeEntity();

        entity.setName(name);

        partTypeRepository.save(entity);
    }

    @Override
    public void addPart(PartRequest request) throws ObjectNotFoundException {
        if (request == null)
            throw new IllegalArgumentException("Incorrect request");

        PartEntity entity = new PartEntity();

        entity.setName(request.getName());
        entity.setBrand_id(request.getBrand());
        entity.setModel_id(request.getModel());
        entity.setPrice(request.getPrice());
        entity.setTypeEntity(partTypeRepository.findById(request.getType())
                .orElseThrow(()-> new ObjectNotFoundException("Part type with this id not found")));
        entity.setCount(0);

        partRepository.save(entity);
    }

    @Override
    public void orderPart(OrderPartRequest orderPartRequest) throws ObjectNotFoundException {
        PartEntity partEntity = partRepository.findById(orderPartRequest.getId())
                .orElseThrow(()-> new ObjectNotFoundException("Part with this id not found"));
        partEntity.setCount(partEntity.getCount() + orderPartRequest.getCount());
        partRepository.save(partEntity);
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

    @Override
    public Part getPartById(Long id) throws ObjectNotFoundException {
        return partToEntityMapper.partEntityToPart(partRepository.findById(id)
                .orElseThrow(()-> new ObjectNotFoundException("Part with this id not found")));
    }

    @Override
    public List<Part> getPartByType(Long id) throws ObjectNotFoundException {
        PartTypeEntity typeEntity = partTypeRepository.findById(id)
                .orElseThrow(()-> new ObjectNotFoundException("Part type with this id not found"));

        Iterable<PartEntity> iterable = partRepository.findByTypeEntity(typeEntity);

        List<Part> parts = new ArrayList<>();
        for (PartEntity entity : iterable)
            parts.add(partToEntityMapper.partEntityToPart(entity));

        return parts;
    }
}