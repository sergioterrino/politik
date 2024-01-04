package com.stb.politik.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserByPhone(String phone) {
        return this.userRepository.findByPhone(phone);
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

}
