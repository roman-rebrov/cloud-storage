package com.cloudstorage.storage.service;

import com.cloudstorage.storage.entity.Account;
import com.cloudstorage.storage.entity.FileEntity;
import com.cloudstorage.storage.entity.Login;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {
    public Login login(Account account);

    public void logout(String authToken);

    public String saveFile(String authToken, MultipartFile file);

    public boolean updateFile(String authToken, String oldFilename, String newFilename);

    public boolean deleteFile(String authToken, String filename);

    public List<FileEntity> getFileList(String authToken, int limit);

    public byte[] getFile(String authToken, String filename);
}
