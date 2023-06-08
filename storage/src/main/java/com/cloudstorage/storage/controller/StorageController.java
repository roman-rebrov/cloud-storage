package com.cloudstorage.storage.controller;


import com.cloudstorage.storage.entity.Account;
import com.cloudstorage.storage.entity.FileEntity;
import com.cloudstorage.storage.entity.Login;
import com.cloudstorage.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@CrossOrigin(
        origins = "http://192.168.0.111:8080",
        allowCredentials = "true"
)
@RestController
@RequestMapping("/cloud")
public class StorageController {

    @Autowired
    private StorageService service;

    @PostMapping("/login")
    public ResponseEntity<Login> login(@RequestBody Account account) {
        final Login login = service.login(account);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("auth-token") String authToken) {
        service.logout(authToken);
    }

    @PostMapping("/file")
    public ResponseEntity<String> filePost(@RequestHeader("auth-token") String authToken,
                                           @RequestBody MultipartFile file) {
        final String response = service.saveFile(authToken, file);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/file")
    public void fileDelete(@RequestHeader("auth-token") String authToken,
                           @RequestParam("filename") String filename) {
        service.deleteFile(authToken, filename);
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> fileGet(@RequestHeader("auth-token") String authToken,
                                          @RequestParam("filename") String filename) {
        final byte[] file = service.getFile(authToken, filename);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity response = new ResponseEntity(file, headers, HttpStatus.OK);
        return response;
    }


    @PutMapping("/file")
    public void filenamePut(@RequestHeader("auth-token") String authToken,
                            @RequestParam("filename") String filename,
                            @RequestBody FileEntity newFilename
    ) {

        service.updateFile(authToken, filename, newFilename.getFilename());
    }


    @GetMapping("/list")
    public ResponseEntity<List<FileEntity>> list(@RequestHeader("auth-token") String authToken,
                                                 @RequestParam("limit") int limit) {
        System.out.println(authToken.substring(authToken.indexOf(" ") + 1));
        final List<FileEntity> fileList = service.getFileList(authToken, limit);
        return ResponseEntity.ok(fileList);
    }


}
