package com.cloudstorage.storage.controller;


import com.cloudstorage.storage.service.StorageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/")
public class StorageController {

    private StorageService service;

    public StorageController(StorageService service) {
        this.service = service;
    }

    @GetMapping
    public String test(){
        return this.service.getTest();
    }
}
