package com.cloudstorage.storage.service;

import com.cloudstorage.storage.entity.Account;
import com.cloudstorage.storage.entity.Login;
import com.cloudstorage.storage.entity.persistence.UserPersistence;
import com.cloudstorage.storage.exception.BadCredentialException;
import com.cloudstorage.storage.exception.InputDataException;
import com.cloudstorage.storage.repository.ClientRepository;
import com.cloudstorage.storage.repository.UserJpaRepository;
import com.cloudstorage.storage.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepository repository;
    @Autowired
    private UserJpaRepository userRepository;

    @Override
    public Login login(Account account) {

        // Account verification
        final UserPersistence verified = userRepository.findByLoginAndPassword(account.getLogin(), account.getPassword());

        // Check of verified account
        if (verified == null) {
            // Send code: 400
            throw new BadCredentialException("Incorrect login or password");
        }
        // Create token
        final Login login = repository.login(verified.getDirectory());

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
}
