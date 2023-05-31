package com.cloudstorage.storage.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginTests {

    private final String authToken = "test";

    @Test
    void getterTest() {

        final Login log = new Login(authToken);

        Assertions.assertEquals(log.getAuthToken(), this.authToken);
    }

    @Test
    void setterTest() {

        final String authTokenCh = "authTokenChanged";
        final Login log = new Login(authToken);

        log.setAuthToken(authTokenCh);

        Assertions.assertEquals(log.getAuthToken(), authTokenCh);
    }
}
