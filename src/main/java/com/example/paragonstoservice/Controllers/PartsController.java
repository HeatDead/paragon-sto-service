package com.example.paragonstoservice.Controllers;

import com.example.paragonstoservice.Exceptions.ObjectNotFoundException;
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

    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    public void addPartType(@RequestParam String name){
        partsService.addPartType(name);
    }

    @RequestMapping(value = "/getAllTypes", method = RequestMethod.GET)
    public List<PartType> getAllPartsTypes(){
        return partsService.getAllPartsTypes();
    }

    @RequestMapping(value = "/allParts", method = RequestMethod.GET)
    public List<Part> getAllParts(){
        return partsService.getAllParts();
    }

    @RequestMapping(value = "/getPartById", method = RequestMethod.GET)
    public Part getPartById(@RequestParam Long id) throws ObjectNotFoundException{
        return partsService.getPartById(id);
    }

    @RequestMapping(value = "/getPartByType", method = RequestMethod.GET)
    public List<Part> getPartByType(@RequestParam Long id) throws ObjectNotFoundException{
        return partsService.getPartByType(id);
    }

    @RequestMapping(value = "/addPart", method = RequestMethod.POST)
    public void addPart(@RequestBody PartRequest partRequest) throws ObjectNotFoundException{
        partsService.addPart(partRequest);
    }

    //Запрос микросервиса
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public void orderPart(@RequestBody OrderPartRequest orderPartRequest) throws ObjectNotFoundException {
        partsService.orderPart(orderPartRequest);
    }
}