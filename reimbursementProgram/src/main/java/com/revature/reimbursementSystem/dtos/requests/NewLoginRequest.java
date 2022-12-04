package com.revature.reimbursementSystem.dtos.requests;

import com.revature.reimbursementSystem.utils.HashBrowns;

import java.security.NoSuchAlgorithmException;

public class NewLoginRequest {
    private String username, password;
    private static final HashBrowns hashBrowns = new HashBrowns();
    public NewLoginRequest() {
        super();
    }

    public NewLoginRequest(String username, String password) throws NoSuchAlgorithmException {
        this.username = username;
        this.password = hashBrowns.encryptString(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.password = hashBrowns.encryptString(password);
    }

    @Override
    public String toString() {
        return "NewLoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
