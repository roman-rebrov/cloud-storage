package com.cloudstorage.storage.entity.persistence;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Files")
public class FilePersistence {

    public FilePersistence() {}

    public FilePersistence(String userDir, String filename, long size) {
        this.userDir = userDir;
        this.filename = filename;
        this.size = size;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "user_dir")
    private String userDir;
    @Column
    private String filename;
    @Column
    private long size;
}
