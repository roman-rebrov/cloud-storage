package com.cloudstorage.storage.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidationUtilsTests {

    private String token = "12345678-123456-781234-5678-12345678";

    @Test
    @DisplayName("Valid true test")
    void validTest() {
        final boolean result = ValidationUtils.tokenValidation(token);
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Empty value test")
    void emptyValueTest() {
        final boolean result = ValidationUtils.tokenValidation("");
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Valid false test")
    void noValidTest() {
        final boolean result = ValidationUtils.tokenValidation(token + "1");
        Assertions.assertFalse(result);
    }

}
