package com.cloudstorage.storage.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Login {

    @JsonProperty("auth-token")
    private String authToken;

    public Login() {}
    public Login(String authToken) {
        this.authToken = authToken;
    }
}
