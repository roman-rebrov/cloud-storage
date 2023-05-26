package com.cloudstorage.storage.service;

import com.cloudstorage.storage.entity.Account;
import com.cloudstorage.storage.entity.FileEntity;
import com.cloudstorage.storage.entity.Login;
import com.cloudstorage.storage.exception.AuthorizedException;
import com.cloudstorage.storage.exception.BadCredentialException;
import com.cloudstorage.storage.exception.InputDataException;
import com.cloudstorage.storage.exception.ServerError;
import com.cloudstorage.storage.repository.StorageRepository;
import com.cloudstorage.storage.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageRepository repository;

    @Override
    public Login login(Account account) {

        // Account verification
        final boolean verified = repository.verify(account);

        // Check of verified account
        if (!verified) {
            // Send code: 400
            throw new BadCredentialException("Incorrect login or password");
        }
        // Create token
        final Login login = repository.login(account);

        return login;
    }

    @Override
    public void logout(String authToken) {

        //  Validation
        final boolean isValid = ValidationUtils.tokenValidation(authToken);
        if (!isValid) {
            // Send code: 400
            throw new InputDataException("Error input data");
        }

        repository.logout(authToken);
    }

    @Override
    public List<FileEntity> getFileList(String authToken, int limit) {

        //  Validation
        final boolean isValid = ValidationUtils.tokenValidation(authToken);
        if (!isValid) {
            // Send code: 400
            throw new InputDataException("Error input data");
        }
        //  Auth checking
        final boolean isAuth = repository.auth(authToken);
        if (!isAuth) {
            // Send code: 401
            throw new AuthorizedException("Unauthorized error");
        }

        final List<FileEntity> fileList = repository.getFileList(limit);
        if (fileList == null || fileList.isEmpty()) {
            // Send code: 500
            throw new ServerError("Service: Server Error in getFileList method");
        }

        return fileList;
    }

    @Override
    public byte[] getFile(String authToken, String filename) {

        //  Validation
        final boolean isValid = ValidationUtils.tokenValidation(authToken);
        if (!isValid) {
            // Send code: 400
            throw new InputDataException("Error input data");
        }
        //  Auth checking
        final boolean isAuth = repository.auth(authToken);
        if (!isAuth) {
            // Send code: 401
            throw new AuthorizedException("Unauthorized error");
        }

        byte[] fileByte = null;
        try {
            fileByte = repository.getFile(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Send code: 500
            throw new ServerError("Service: FileNotFoundException in getFile method");
        } catch (IOException e) {
            e.printStackTrace();
            // Send code: 500
            throw new ServerError("Service: Server Error in getFile method");
        }
        return fileByte;
    }

    @Override
    public String saveFile(String authToken, MultipartFile file) {

        //  Validation
        final boolean isValid = ValidationUtils.tokenValidation(authToken);
        if (!isValid) {
            // Send code: 400
            throw new InputDataException("Error input data");
        }
        //  Auth checking
        final boolean isAuth = repository.auth(authToken);
        if (!isAuth) {
            // Send code: 401
            throw new AuthorizedException("Unauthorized error");
        }
        repository.saveFile(file);

        return "OK";
    }

    @Override
    public boolean updateFile(String authToken, String oldFilename, String newFilename) {

        //  Validation
        final boolean isValid = ValidationUtils.tokenValidation(authToken);
        if (!isValid) {
            // Send code: 400
            throw new InputDataException("Error input data");
        }
        //  Auth checking
        final boolean isAuth = repository.auth(authToken);
        if (!isAuth) {
            // Send code: 401
            throw new AuthorizedException("Unauthorized error");
        }

        final boolean result = repository.updateFile(oldFilename, newFilename);
        if (!result) {
            // Send code: 500
            throw new ServerError("Service: Server Error in updateFile method");
        }
        return result;
    }

    @Override
    public boolean deleteFile(String authToken, String filename) {

        //  Validation
        final boolean isValid = ValidationUtils.tokenValidation(authToken);
        if (!isValid) {
            // Send code: 400
            throw new InputDataException("Error input data");
        }
        //  Auth checking
        final boolean isAuth = repository.auth(authToken);
        if (!isAuth) {
            // Send code: 401
            throw new AuthorizedException("Unauthorized error");
        }
        final boolean result = repository.deleteFile(filename);
        if (!result) {
            // Send code: 500
            throw new ServerError("Service: Server Error in deleteFile method");
        }
        return result;
    }
}