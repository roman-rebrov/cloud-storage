package com.cloudstorage.storage.repository;

import org.springframework.stereotype.Repository;

@Repository
public class StorageRepository {

    private String testMessage = "Hallo, world!!";


    public String getTest(){
        return this.testMessage;
    }
}
