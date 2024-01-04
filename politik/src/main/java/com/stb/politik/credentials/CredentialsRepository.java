package com.stb.politik.credentials;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stb.politik.user.User;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, Long> {
    Credentials findByUser(User user);
}