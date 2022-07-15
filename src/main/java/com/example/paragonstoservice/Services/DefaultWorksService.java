package com.example.paragonstoservice.Services;

import com.example.paragonstoservice.Entities.PartEntity;
import com.example.paragonstoservice.Entities.WorkEntity;
import com.example.paragonstoservice.Entities.WorkTypeEntity;
import com.example.paragonstoservice.Mappers.WorkToEntityMapper;
import com.example.paragonstoservice.Mappers.WorkTypeToEntityMapper;
import com.example.paragonstoservice.Objects.Part;
import com.example.paragonstoservice.Objects.Work;
import com.example.paragonstoservice.Objects.WorkType;
import com.example.paragonstoservice.Repositories.WorkRepository;
import com.example.paragonstoservice.Repositories.WorkTypeRepository;
import com.example.paragonstoservice.Requests.WorkRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultWorksService implements WorksService{
    private final WorkTypeRepository workTypeRepository;
    private final WorkRepository workRepository;

    private final WorkToEntityMapper workToEntityMapper;
    private final WorkTypeToEntityMapper workTypeToEntityMapper;

    @Override
    public void addWorkType(String name) {
        if (name == null)
            return;//ex

        WorkTypeEntity entity = new WorkTypeEntity();

        entity.setName(name);

        workTypeRepository.save(entity);
    }

    @Override
    public void addWork(WorkRequest request) {
        if (request == null)
            return;//ex

        WorkEntity entity = new WorkEntity();

        entity.setOrder_id(request.getOrder());
        entity.setWork_desc(request.getDescription());
        entity.setWork_price(request.getPrice());

        workRepository.save(entity);
    }

    @Override
    public List<WorkType> getAllWorksTypes() {
        Iterable<WorkTypeEntity> iterable = workTypeRepository.findAll();

        List<WorkType> workTypes = new ArrayList<>();
        for (WorkTypeEntity entity : iterable)
            workTypes.add(workTypeToEntityMapper.workTypeEntityToWorkType(entity));

        return workTypes;
    }

    @Override
    public List<Work> getAllWorksByOrderId(Long id) {
        if (id == null)
            return null;//ex
        Iterable<WorkEntity> iterable = workRepository.findAllByOrderId(id);

        List<Work> works = new ArrayList<>();
        for (WorkEntity entity : iterable)
            works.add(workToEntityMapper.workEntityToWork(entity));

        return works;
    }
}
