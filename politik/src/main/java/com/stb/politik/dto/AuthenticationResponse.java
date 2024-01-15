package com.stb.politik.dto;

import com.stb.politik.user.User;

public class AuthenticationResponse {

    private String jwt;
    private User user;

    public AuthenticationResponse(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }
    public AuthenticationResponse (User user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
