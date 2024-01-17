package com.stb.politik.user;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserByPhone(String phone);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    User getUserByUserId(Long userId);
    void saveUser(User user);
}
