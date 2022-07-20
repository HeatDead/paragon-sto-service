package com.example.paragonstoservice.Services;

import com.example.paragonstoservice.Entities.PartEntity;
import com.example.paragonstoservice.Entities.UsedPartsEntity;
import com.example.paragonstoservice.Entities.WorkEntity;
import com.example.paragonstoservice.Entities.WorkTypeEntity;
import com.example.paragonstoservice.Exceptions.ObjectNotFoundException;
import com.example.paragonstoservice.Mappers.PartToEntityMapper;
import com.example.paragonstoservice.Mappers.UsedPartsToEntityMapper;
import com.example.paragonstoservice.Mappers.WorkToEntityMapper;
import com.example.paragonstoservice.Mappers.WorkTypeToEntityMapper;
import com.example.paragonstoservice.Objects.Part;
import com.example.paragonstoservice.Objects.UsedPart;
import com.example.paragonstoservice.Objects.Work;
import com.example.paragonstoservice.Objects.WorkType;
import com.example.paragonstoservice.Repositories.PartRepository;
import com.example.paragonstoservice.Repositories.UsedPartsRepository;
import com.example.paragonstoservice.Repositories.WorkRepository;
import com.example.paragonstoservice.Repositories.WorkTypeRepository;
import com.example.paragonstoservice.Requests.WorkPartRequest;
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
    private final PartRepository partRepository;
    private final UsedPartsRepository usedPartsRepository;

    private final WorkToEntityMapper workToEntityMapper;
    private final WorkTypeToEntityMapper workTypeToEntityMapper;
    private final UsedPartsToEntityMapper usedPartsToEntityMapper;
    private final PartToEntityMapper partToEntityMapper;

    @Override
    public void addWorkType(String name) {
        if (name.equals(""))
            throw new IllegalArgumentException("Неверное имя");

        WorkTypeEntity entity = new WorkTypeEntity();

        entity.setName(name);

        workTypeRepository.save(entity);
    }

    @Override
    public void addWork(WorkRequest request) throws ObjectNotFoundException {
        if (request == null)
            throw new IllegalArgumentException("Неверный запрос");

        if(request.getDescription().equals(""))
            throw new IllegalArgumentException("Описание не может быть пустым");

        WorkEntity entity = new WorkEntity();

        entity.setOrder_id(request.getOrder());
        entity.setWork_desc(request.getDescription());
        entity.setWork_price(request.getWork_price());

        for (WorkPartRequest usedPart : request.getUsed_parts()) {
            PartEntity partEntity = partRepository.findById(usedPart.getId())
                    .orElseThrow(() -> new ObjectNotFoundException("Запчасть с этим id не найдена"));
            if (usedPart.getCount() <= 0)
                throw new IllegalArgumentException("Неверное количество запчастей");
            if (partEntity.getCount() < usedPart.getCount())
                throw new IllegalArgumentException("Не хватает запчастей на складе");
        }

        entity = workRepository.save(entity);

        Double parts_price = 0.0;
        for (WorkPartRequest usedPart : request.getUsed_parts()){
            PartEntity partEntity = partRepository.findById(usedPart.getId()).get();

            int count = usedPart.getCount();

            parts_price += partEntity.getPrice() * count;
            partEntity.setCount(partEntity.getCount() - count);

            UsedPartsEntity usedPartsEntity = new UsedPartsEntity();
            usedPartsEntity.setWork_id(entity.getId());
            usedPartsEntity.setPart_id(partEntity.getId());
            usedPartsEntity.setCount(count);

            usedPartsRepository.save(usedPartsEntity);
        }

        entity.setTotal_price(entity.getWork_price() + parts_price);

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
    public List<Work> getAllWorksByOrderId(Long id) throws ObjectNotFoundException {
        if (id == null)
            throw new IllegalArgumentException("Неверный запрос");

        Iterable<WorkEntity> iterable = workRepository.findAllByOrderId(id);

        List<Work> works = new ArrayList<>();
        for (WorkEntity entity : iterable) {
            Work w = workToEntityMapper.workEntityToWork(entity);

            Iterable<UsedPartsEntity> upIterable = usedPartsRepository.findAllByWork_id(w.getId());

            List<UsedPart> usedParts = new ArrayList<>();
            for (UsedPartsEntity usedPartsEntity : upIterable) {

                Part p = partToEntityMapper.partEntityToPart(partRepository.findById(usedPartsEntity.getPart_id())
                        .orElseThrow(()-> new ObjectNotFoundException("Запчасть с этим id не найдена")));
                usedPartsEntity.setName(p.getName());
                usedPartsEntity.setBrand_id(p.getBrand_id());
                usedPartsEntity.setModel_id(p.getModel_id());
                usedPartsEntity.setPrice(p.getPrice());

                usedParts.add(usedPartsToEntityMapper.usedPartsEntityToUsedParts(usedPartsEntity));
            }

            w.setDescription(entity.getWork_desc());
            w.setPrice(entity.getWork_price());
            w.setUsed_parts(usedParts);
            works.add(w);
        }

        return works;
    }
}
