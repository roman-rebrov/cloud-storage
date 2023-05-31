package com.cloudstorage.storage.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTests {

    private final String log = "user";
    private final String pass = "123";

    @Test
    void getterTest() {

        final Account acc = new Account(log, pass);

        Assertions.assertEquals(acc.getLogin(), log);
        Assertions.assertEquals(acc.getPassword(), pass);
    }

    @Test
    void setterTest() {

        final String logCh = "logChanged";
        final String passCh = "passChanged";

        final Account acc = new Account(log, pass);
        acc.setLogin(logCh);
        acc.setPassword(passCh);

        Assertions.assertEquals(acc.getLogin(), logCh);
        Assertions.assertEquals(acc.getPassword(), passCh);
    }
}
