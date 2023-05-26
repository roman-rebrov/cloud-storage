package com.cloudstorage.storage.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Login {

    @JsonProperty("auth-token")
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Login(String authToken) {
        this.authToken = authToken;
    }
}
