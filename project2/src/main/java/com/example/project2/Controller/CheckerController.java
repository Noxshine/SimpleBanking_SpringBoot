package com.example.project2.Controller;

import com.example.project2.Repository.AccRepo;
import com.example.project2.Service.AccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checker")
public class CheckerController {

    @Autowired
    private AccRepo accRepo;

    @Autowired
    private AccService accService;

    @GetMapping("/test")
    public String hello(){
        return "Checker content";
    }

    @GetMapping("/")
    public void checking(){
    }
}
