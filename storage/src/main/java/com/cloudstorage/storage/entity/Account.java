package com.cloudstorage.storage.entity;

import lombok.Data;

@Data
public class Account {

    private String login;
    private String password;
    private String directory;

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
