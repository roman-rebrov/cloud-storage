package com.cloudstorage.storage.util;

public class ValidationUtils {

    public static boolean tokenValidation(String token) {

        final String tk = token.substring(token.indexOf(" ") + 1);
        if (tk.length() == 36) {
            return true;
        }
        return false;
    }
}
