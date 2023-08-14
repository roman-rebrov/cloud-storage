package com.cloudstorage.storage.entity;

import lombok.Data;

@Data
public class FileEntity {

    private String filename;
    private long size;

    public FileEntity(String name, long size) {
        this.filename = name;
        this.size = size;
    }
}
