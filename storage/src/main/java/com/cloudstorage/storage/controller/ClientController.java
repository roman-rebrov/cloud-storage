package com.cloudstorage.storage.controller;

import com.cloudstorage.storage.entity.Account;
import com.cloudstorage.storage.entity.Login;
import com.cloudstorage.storage.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(
        origins = "${origins.clients}",
        allowCredentials = "true"
)
@RequestMapping("/cloud")
public class ClientController {

    @Autowired
    public ClientService service;


    @PostMapping("/login")
    public ResponseEntity<Login> login(@RequestBody Account account) {
        final Login login = service.login(account);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("auth-token") String authToken) {
        service.logout(authToken);
    }

}
