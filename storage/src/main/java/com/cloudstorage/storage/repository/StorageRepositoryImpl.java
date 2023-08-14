package com.cloudstorage.storage.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

@Repository
public class StorageRepositoryImpl implements StorageRepository {

    @Value("${file.directory.root}")
    private String STORE_DIR;
    private Logger logger = Logger.getLogger(StorageRepositoryImpl.class.getName());


    @Override
    public boolean saveFile(String directory, MultipartFile file) {

        final File f = new File(STORE_DIR + "/" + directory );

        if (!f.isDirectory()){
            f.mkdir();
        }

        try (
                FileOutputStream writer = new FileOutputStream(f.getPath() + "/" + file.getOriginalFilename());
        ) {

            this.logger.info("file: " + file.getSize());

            writer.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateFile(String directory, String oldFilename, String newFilename) {

        final File file = new File(STORE_DIR + "/" + directory + "/" + oldFilename);
        if (file.isFile()) {
            final boolean res = new File(STORE_DIR + "/" + directory + "/" + oldFilename).renameTo(new File(STORE_DIR + "/" + directory + "/" + newFilename));
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteFile(String directory, String filename) {

        final File file = new File(STORE_DIR + "/" + directory + "/" + filename);
        if (file.isFile()) {
            file.delete();
            return true;
        }
        return false;
    }

    @Override
    public byte[] getFile(String dir, String filename) throws IOException {

        final File file = new File(STORE_DIR + "/" + dir + "/" + filename);
        byte[] result = new byte[0];

        if (file.isFile()) {
            result = Files.readAllBytes(file.toPath());
        }

        return result;
    }
}
