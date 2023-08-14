package com.cloudstorage.storage.service;

import com.cloudstorage.storage.entity.FileEntity;
import com.cloudstorage.storage.entity.persistence.FilePersistence;
import com.cloudstorage.storage.exception.AuthorizedException;
import com.cloudstorage.storage.exception.InputDataException;
import com.cloudstorage.storage.exception.ServerError;
import com.cloudstorage.storage.repository.ClientRepository;
import com.cloudstorage.storage.repository.FilesJpaRepository;
import com.cloudstorage.storage.repository.StorageRepository;
import com.cloudstorage.storage.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private ClientRepository clientRepo;
    @Autowired
    private StorageRepository repository;
    @Autowired
    private FilesJpaRepository filenameRepository;

    @Override
    public List<FileEntity> getFileList(String authToken, int limit) {

        //  Validation
        final boolean isValid = ValidationUtils.tokenValidation(authToken);
        if (!isValid) {
            // Send code: 400
            throw new InputDataException("Error input data");
        }
        //  Auth checking
        final boolean isAuth = clientRepo.auth(authToken);
        if (!isAuth) {
            // Send code: 401
            throw new AuthorizedException("Unauthorized error");
        }

        final String directory = clientRepo.getDir(authToken);
        final List<FilePersistence> fileList = filenameRepository.findByDirname(directory, limit);

        if (fileList == null) {
            // Send code: 500
            throw new ServerError("Service: Server Error in getFileList method");
        }

        return fileList.stream().<FileEntity>map((FilePersistence file) ->
                new FileEntity(file.getFilename(), file.getSize())).collect(Collectors.toList());
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
        final boolean isAuth = clientRepo.auth(authToken);
        if (!isAuth) {
            // Send code: 401
            throw new AuthorizedException("Unauthorized error");
        }

        byte[] fileByte = null;
        final String directory = clientRepo.getDir(authToken);

        try {
            fileByte = repository.getFile(directory, filename);
            if (fileByte == null) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
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
        final boolean isAuth = clientRepo.auth(authToken);
        if (!isAuth) {
            // Send code: 401
            throw new AuthorizedException("Unauthorized error");
        }

        final String dir = clientRepo.getDir(authToken);

        // Save in file directory.
        repository.saveFile(dir, file);

        // Save in DB files.
        final String name = file.getOriginalFilename();
        final long size = file.getSize();
        filenameRepository.save(dir, name, size);

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
        final boolean isAuth = clientRepo.auth(authToken);
        if (!isAuth) {
            // Send code: 401
            throw new AuthorizedException("Unauthorized error");
        }

        final String dir = clientRepo.getDir(authToken);
        final boolean result = repository.updateFile(dir, oldFilename, newFilename);
        filenameRepository.updateFilename(dir, oldFilename, newFilename);

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
        final boolean isAuth = clientRepo.auth(authToken);
        if (!isAuth) {
            // Send code: 401
            throw new AuthorizedException("Unauthorized error");
        }

        final String dir = clientRepo.getDir(authToken);
        final boolean result = repository.deleteFile(dir, filename);
        filenameRepository.deleteFilename(dir, filename);

        if (!result) {
            // Send code: 500
            throw new ServerError("Service: Server Error in deleteFile method");
        }
        return result;
    }
}