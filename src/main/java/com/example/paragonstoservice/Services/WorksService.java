package com.example.paragonstoservice.Services;

import com.example.paragonstoservice.Exceptions.ObjectNotFoundException;
import com.example.paragonstoservice.Objects.Work;
import com.example.paragonstoservice.Objects.WorkType;
import com.example.paragonstoservice.Requests.WorkRequest;

import java.util.List;

public interface WorksService {
    void addWorkType(String name);
    void addWork(WorkRequest request) throws ObjectNotFoundException;
    List<WorkType> getAllWorksTypes();
    List<Work> getAllWorksByOrderId(Long id) throws ObjectNotFoundException;
}
