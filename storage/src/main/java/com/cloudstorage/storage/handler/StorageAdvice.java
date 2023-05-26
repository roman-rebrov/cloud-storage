package com.cloudstorage.storage.handler;

import com.cloudstorage.storage.entity.Error;
import com.cloudstorage.storage.exception.AuthorizedException;
import com.cloudstorage.storage.exception.BadCredentialException;
import com.cloudstorage.storage.exception.InputDataException;
import com.cloudstorage.storage.exception.ServerError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Logger;

@RestControllerAdvice
public class StorageAdvice {

    private Logger logger = Logger.getLogger(StorageAdvice.class.getName());

    @ExceptionHandler(BadCredentialException.class)
    public ResponseEntity<Error> badCredential(BadCredentialException exception) {
        this.logger.info(exception.getMessage());
        final Error message = new Error("Incorrect login or password");
        ResponseEntity response = new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        return response;
    }

    @ExceptionHandler(InputDataException.class)
    public ResponseEntity<Error> authError(InputDataException exception) {
        this.logger.info(exception.getMessage());
        final Error message = new Error("Error input data");
        ResponseEntity response = new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        return response;
    }

    @ExceptionHandler(AuthorizedException.class)
    public ResponseEntity<Error> authError(AuthorizedException exception) {
        this.logger.info(exception.getMessage());
        final Error message = new Error("Unauthorized error");
        ResponseEntity response = new ResponseEntity(message, HttpStatus.UNAUTHORIZED);
        return response;
    }

    @ExceptionHandler(ServerError.class)
    public ResponseEntity<Error> serverError(ServerError exception) {
        this.logger.info(exception.getMessage());
        final Error message = new Error("Server Error");
        ResponseEntity response = new ResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }

}
