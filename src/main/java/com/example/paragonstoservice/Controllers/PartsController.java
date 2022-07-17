package com.example.paragonstoservice.Controllers;

import com.example.paragonstoservice.Objects.Part;
import com.example.paragonstoservice.Objects.PartType;
import com.example.paragonstoservice.Requests.OrderPartRequest;
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

    @PostMapping("/addType")
    public void addPartType(@RequestParam String name){
        partsService.addPartType(name);
    }

    @GetMapping("/getAllTypes")
    public List<PartType> getAllPartsTypes(){
        return partsService.getAllPartsTypes();
    }

    @GetMapping("/allParts")
    public List<Part> getAllParts(){
        return partsService.getAllParts();
    }

    @GetMapping("/getPartById")
    public Part getPartById(@RequestParam Long id){
        return partsService.getPartById(id);
    }

    @GetMapping("/getPartByType")
    public List<Part> getPartByType(@RequestParam Long id){
        return partsService.getPartByType(id);
    }

    @PostMapping("/addPart")
    public void addPart(@RequestBody PartRequest partRequest){
        partsService.addPart(partRequest);
    }

    //Запрос микросервиса
    @PostMapping("/order")
    public void orderPart(@RequestBody OrderPartRequest orderPartRequest){
        partsService.orderPart(orderPartRequest);
    }
}