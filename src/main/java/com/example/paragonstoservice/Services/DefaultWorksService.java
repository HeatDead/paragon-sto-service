package com.example.paragonstoservice.Services;

import com.example.paragonstoservice.Entities.PartEntity;
import com.example.paragonstoservice.Entities.UsedPartsEntity;
import com.example.paragonstoservice.Entities.WorkEntity;
import com.example.paragonstoservice.Entities.WorkTypeEntity;
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

        //TO DO: добавить проверку заказа
        WorkEntity entity = new WorkEntity();

        entity.setOrder_id(request.getOrder());
        entity.setWork_desc(request.getDescription());
        entity.setWork_price(request.getWork_price());

        entity = workRepository.save(entity);

        System.out.println(request.getUsed_parts());

        Double parts_price = 0.0;
        for (int i = 0; i < request.getUsed_parts().size(); i++){
            PartEntity partEntity = partRepository.findById(request.getUsed_parts().get(i).getId()).get();
            int count = request.getUsed_parts().get(i).getCount();

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
    public List<Work> getAllWorksByOrderId(Long id) {
        if (id == null)
            return null;//ex
        Iterable<WorkEntity> iterable = workRepository.findAllByOrderId(id);

        List<Work> works = new ArrayList<>();
        for (WorkEntity entity : iterable) {
            Work w = workToEntityMapper.workEntityToWork(entity);

            Iterable upIterable = usedPartsRepository.findAllByWork_id(w.getId());

            List<UsedPart> usedParts = new ArrayList<>();
            for (var usedPartsEntity : upIterable) {
                UsedPartsEntity upEntity = (UsedPartsEntity)usedPartsEntity;

                Part p = partToEntityMapper.partEntityToPart(partRepository.findById(upEntity.getPart_id()).get());
                upEntity.setName(p.getName());
                upEntity.setBrand_id(p.getBrand_id());
                upEntity.setModel_id(p.getModel_id());
                upEntity.setPrice(p.getPrice());

                usedParts.add(usedPartsToEntityMapper.usedPartsEntityToUsedParts(upEntity));
            }

            w.setUsed_parts(usedParts);
            works.add(w);
        }

        return works;
    }
}
