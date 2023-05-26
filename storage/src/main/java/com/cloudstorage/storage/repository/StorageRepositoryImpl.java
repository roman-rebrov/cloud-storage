package com.cloudstorage.storage.repository;

import com.cloudstorage.storage.controller.StorageController;
import com.cloudstorage.storage.entity.Account;
import com.cloudstorage.storage.entity.FileEntity;
import com.cloudstorage.storage.entity.Login;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Repository
public class StorageRepositoryImpl implements StorageRepository {

    private final String STORE_DIR = "storage/src/main/resources/static";
    private final Map<String, Account> accountStorage = new ConcurrentHashMap<>();
    private final Map<String, Account> tokenStorage = new ConcurrentHashMap<>();
    private Logger logger = Logger.getLogger(StorageController.class.getName());

    public StorageRepositoryImpl() {
        final Account newAcc = new Account("321", "654");
        accountStorage.put(newAcc.getLogin(), newAcc);
    }

    @Override
    public Login login(Account account) {
        final UUID uuid = UUID.randomUUID();
        this.tokenStorage.put(uuid.toString(), account);
        return new Login(uuid.toString());
    }

    @Override
    public void logout(String token) {
        final String tk = token.substring(token.indexOf(" ") + 1);
        if (this.tokenStorage.containsKey(tk)) {
            this.tokenStorage.remove(tk);
        }
    }

    @Override
    public synchronized boolean verify(Account account) {

        if (this.accountStorage.containsKey(account.getLogin())) {
            final Account acc = this.accountStorage.get(account.getLogin());

            if (acc.getLogin().equals(account.getLogin()) && acc.getPassword().equals(account.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean auth(String authToken) {
        final String tk = authToken.substring(authToken.indexOf(" ") + 1);
        return tokenStorage.containsKey(tk);
    }

    @Override
    public List<FileEntity> getFileList(int limit) {

        final List<FileEntity> list = new ArrayList<>();
        final File files = new File(STORE_DIR);
        int fileLimit = limit;

        for (File f : files.listFiles()) {
            if (f.isFile()) {
                try {

                    final FileEntity file = new FileEntity(f.getName(), Files.size(f.toPath()));
                    list.add(file);
                    fileLimit--;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileLimit <= 0) {
                break;
            }
        }

        return list;
    }

    @Override
    public boolean saveFile(MultipartFile file) {
        try (
                FileOutputStream writer = new FileOutputStream(STORE_DIR + "/" + file.getOriginalFilename());
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
    public boolean updateFile(String oldFilename, String newFilename) {

        final File file = new File(STORE_DIR + "/" + oldFilename);
        if (file.isFile()) {
            final boolean res = new File(STORE_DIR + "/" + oldFilename).renameTo(new File(STORE_DIR + "/" + newFilename));
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteFile(String filename) {

        final File file = new File(STORE_DIR + "/" + filename);
        if (file.isFile()) {
            file.delete();
            return true;
        }
        return false;
    }

    @Override
    public byte[] getFile(String filename) throws IOException {

        final File file = new File(STORE_DIR + "/" + filename);
        byte[] result = new byte[0];

        if (file.isFile()) {
            result = Files.readAllBytes(file.toPath());
        }

        return result;
    }


}
