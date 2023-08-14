package com.cloudstorage.storage.service;


import com.cloudstorage.storage.entity.Account;
import com.cloudstorage.storage.entity.Login;

public interface ClientService {

    public Login login(Account account);

    public void logout(String authToken);
}
