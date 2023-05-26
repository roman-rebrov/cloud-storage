package com.cloudstorage.storage.repository;

import com.cloudstorage.storage.entity.Account;
import com.cloudstorage.storage.entity.FileEntity;
import com.cloudstorage.storage.entity.Login;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StorageRepository {

    public Login login(Account account);

    public void logout(String token);

    public boolean verify(Account account);

    public boolean auth(String authToken);

    public List<FileEntity> getFileList(int limit);

    public boolean saveFile(MultipartFile file);

    public boolean updateFile(String oldFilename, String newFilename);

    public boolean deleteFile(String filename);

    public byte[] getFile(String filename) throws IOException;
}
