package com.cloudstorage.storage.repository;

import com.cloudstorage.storage.entity.Login;

public interface ClientRepository {

    public Login login(String directory);

    public void logout(String token);

    public boolean auth(String token);

    public String getDir(String token);
}
