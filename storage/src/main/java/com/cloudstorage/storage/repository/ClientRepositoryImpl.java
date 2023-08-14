package com.cloudstorage.storage.repository;

import com.cloudstorage.storage.entity.Login;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ClientRepositoryImpl implements ClientRepository{

    private final Map<String, String> tokenStorage = new ConcurrentHashMap<>();


    @Override
    public Login login(String directory) {
        final UUID uuid = UUID.randomUUID();
        this.tokenStorage.put(uuid.toString(), directory);
        return new Login(uuid.toString());
    }

    @Override
    public void logout(String t) {
        final String token = t.substring(t.indexOf(" ") + 1);
        if (this.tokenStorage.containsKey(token)) {
            this.tokenStorage.remove(token);
        }
    }

    @Override
    public boolean auth(String t) {
        final String token = t.substring(t.indexOf(" ") + 1);
        return tokenStorage.containsKey(token);
    }

    @Override
    public String getDir(String t) {
        final String token = t.substring(t.indexOf(" ") + 1);
        return tokenStorage.get(token);
    }

}
