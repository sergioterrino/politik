package com.stb.politik.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stb.politik.user.User;
import com.stb.politik.user.UserController;
import com.stb.politik.user.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        log.info("User en loadUserByUsername ->" + user);
        if (user == null) {
            log.info("User not found en loadUserByUsername ->" + user);
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsImpl(user);
    }
}