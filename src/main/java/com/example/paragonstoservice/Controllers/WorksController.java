package com.example.paragonstoservice.Controllers;

import com.example.paragonstoservice.Exceptions.ObjectNotFoundException;
import com.example.paragonstoservice.Objects.Work;
import com.example.paragonstoservice.Objects.WorkType;
import com.example.paragonstoservice.Requests.WorkRequest;
import com.example.paragonstoservice.Services.WorksService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/works")
@CrossOrigin
@RequiredArgsConstructor
public class WorksController {
    private final WorksService worksService;

    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    public void addWorkType(@RequestParam String name){
        worksService.addWorkType(name);
    }

    @RequestMapping(value = "/getTypes", method = RequestMethod.GET)
    public List<WorkType> getWorksTypes(){
        return worksService.getAllWorksTypes();
    }

    @RequestMapping(value = "/getWorksById", method = RequestMethod.GET)
    public List<Work> getWorksByOrderId(@RequestParam Long order_id) throws ObjectNotFoundException {
        return worksService.getAllWorksByOrderId(order_id);
    }

    @RequestMapping(value = "/addWork", method = RequestMethod.POST)
    public void addWork(@RequestBody WorkRequest workRequest) throws ObjectNotFoundException{
        worksService.addWork(workRequest);
    }
}