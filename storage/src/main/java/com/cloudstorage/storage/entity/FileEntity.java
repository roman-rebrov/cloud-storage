package com.cloudstorage.storage.entity;

public class FileEntity {

    private String filename;
    private long size;

    public FileEntity(String name, long size) {
        this.filename = name;
        this.size = size;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
