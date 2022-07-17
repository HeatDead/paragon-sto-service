package com.example.paragonstoservice.Services;

import com.example.paragonstoservice.Objects.Part;
import com.example.paragonstoservice.Objects.PartType;
import com.example.paragonstoservice.Requests.OrderPartRequest;
import com.example.paragonstoservice.Requests.PartRequest;

import java.util.List;

public interface PartsService {
    void addPartType(String name);
    void addPart(PartRequest request);
    void orderPart(OrderPartRequest orderPartRequest);
    List<PartType> getAllPartsTypes();
    List<Part> getAllParts();
    Part getPartById(Long id);
    List<Part> getPartByType(Long id);
}
