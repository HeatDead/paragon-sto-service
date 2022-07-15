package com.example.paragonstoservice.Services;

import com.example.paragonstoservice.Objects.Part;
import com.example.paragonstoservice.Objects.PartType;
import com.example.paragonstoservice.Requests.PartRequest;

import java.util.List;

public interface PartsService {
    void addPartType(String name);
    void addPart(PartRequest request);
    List<PartType> getAllPartsTypes();
    List<Part> getAllParts();
}
