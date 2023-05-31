package com.cloudstorage.storage.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ErrorTests {

    private final String message = "test";

    @Test
    void getterTest() {

        final Error err = new Error(this.message);

        Assertions.assertEquals(err.getMessage(), this.message);
    }

    @Test
    void setterTest() {

        final String messageCh = "testChanged";
        final Error err = new Error(this.message);

        err.setMessage(messageCh);

        Assertions.assertEquals(err.getMessage(), messageCh);
    }
}
