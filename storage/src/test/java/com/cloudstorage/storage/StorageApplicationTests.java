package com.cloudstorage.storage;

import com.cloudstorage.storage.controller.StorageController;
import com.cloudstorage.storage.entity.Account;
import com.cloudstorage.storage.entity.Login;
import com.cloudstorage.storage.util.ValidationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StorageApplicationTests {


    @Autowired
    private StorageController controller;

    @Test
    void contextLoads() {
    }

    @Test
    void controllerTest() {
        ResponseEntity<Login> response = this.controller.login(new Account("user", "123"));
        System.out.println("response = " + response.getBody().getAuthToken());
        Assertions.assertTrue(ValidationUtils.tokenValidation(response.getBody().getAuthToken()));
        Assertions.assertNotNull(controller);
    }

}
