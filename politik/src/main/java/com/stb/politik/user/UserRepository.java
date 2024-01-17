package com.stb.politik.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByPhone(String phone);

  User findByEmail(String email);

  User findByUsername(String username);

User findByUserId(Long userId);

  

}
