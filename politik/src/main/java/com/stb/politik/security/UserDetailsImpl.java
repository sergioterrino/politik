package com.stb.politik.security;

import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.stb.politik.user.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor // para que se cree el constructor con todos los atributos
public class UserDetailsImpl implements UserDetails {

    private static Logger log = LoggerFactory.getLogger(UserDetailsImpl.class.getName());

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        log.info("UserDetailsImpl.getPassword() ->" + user.getCredentials().getPasswordHash());
        return user.getCredentials().getPasswordHash();
    }

    @Override
    public String getUsername() {
        log.info("UserDetailsImpl.getUsername() ->" + user.getUsername());
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}