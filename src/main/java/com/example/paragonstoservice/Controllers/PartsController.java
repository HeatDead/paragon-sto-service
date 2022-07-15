package com.example.paragonstoservice.Controllers;

import com.example.paragonstoservice.Objects.Part;
import com.example.paragonstoservice.Objects.PartType;
import com.example.paragonstoservice.Requests.PartRequest;
import com.example.paragonstoservice.Services.PartsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parts")
@CrossOrigin
@RequiredArgsConstructor
public class PartsController {
    private final PartsService partsService;

    @PostMapping("/types")
    public void addPartType(@RequestParam String name){
        partsService.addPartType(name);
    }

    @GetMapping("/types")
    public List<PartType> getAllPartsTypes(){
        return partsService.getAllPartsTypes();
    }

    @GetMapping
    public List<Part> getAllParts(){
        return partsService.getAllParts();
    }

    //Запрос микросервиса
    @PostMapping
    public void addPart(PartRequest partRequest){

    }
}