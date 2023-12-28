package com.stb.politik.credentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialsServiceImpl implements CredentialsService{

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Override
    public void saveCredentials(Credentials credentials) {
        this.credentialsRepository.save(credentials);
    }
    
}
