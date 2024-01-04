package com.stb.politik.dto;

public class LoginRequest {
    private String namePhoneEmail;
    private String password;
    private int index;
    
    public String getNamePhoneEmail() {
        return namePhoneEmail;
    }
    public void setNamePhoneEmail(String namePhoneEmail) {
        this.namePhoneEmail = namePhoneEmail;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

}
