package com.cloudstorage.storage.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileEntityTests {

    private String filename = "test";
    private long size = 1L;


    @Test
    void getterTest() {

        final FileEntity file = new FileEntity(this.filename, this.size);

        Assertions.assertEquals(file.getFilename(), this.filename);
        Assertions.assertEquals(file.getSize(), this.size);
    }

    @Test
    void setterTest() {

        final String filenameChanged = "testChanged";
        final long sizeChanged = 2L;

        final FileEntity file = new FileEntity(this.filename, this.size);

        file.setFilename(filenameChanged);
        file.setSize(sizeChanged);

        Assertions.assertEquals(file.getFilename(), filenameChanged);
        Assertions.assertEquals(file.getSize(), sizeChanged);
    }

}
