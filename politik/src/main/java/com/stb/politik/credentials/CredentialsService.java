package com.stb.politik.credentials;

import com.stb.politik.user.User;

public interface CredentialsService {
    void saveCredentials(Credentials credentials);
    Credentials getCredentialsByUser(User user);
}
