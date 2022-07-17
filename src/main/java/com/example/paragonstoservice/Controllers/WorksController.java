package com.example.paragonstoservice.Controllers;

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

    @PostMapping("/addType")
    public void addWorkType(@RequestParam String name){
        worksService.addWorkType(name);
    }

    @GetMapping("/getTypes")
    public List<WorkType> getWorksTypes(){
        return worksService.getAllWorksTypes();
    }

    @GetMapping("/getWorksById")
    public List<Work> getWorksByOrderId(@RequestParam Long order_id){
        return worksService.getAllWorksByOrderId(order_id);
    }

    @PostMapping("/addWork")
    public void addWork(@RequestBody WorkRequest workRequest){
        worksService.addWork(workRequest);
    }
}