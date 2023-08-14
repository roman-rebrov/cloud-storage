package com.cloudstorage.storage.repository;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface StorageRepository {

    public boolean saveFile(String dir, MultipartFile file);

    public boolean updateFile(String dir, String oldFilename, String newFilename);

    public boolean deleteFile(String dir, String filename);

    public byte[] getFile(String dir, String filename) throws IOException;
}
