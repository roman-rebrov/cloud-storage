package com.cloudstorage.storage.service;


import com.cloudstorage.storage.repository.StorageRepository;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

    private StorageRepository repository;

    public StorageService(StorageRepository repo){
        this.repository = repo;
    }

    public String getTest(){
        return this.repository.getTest();
    }
}
