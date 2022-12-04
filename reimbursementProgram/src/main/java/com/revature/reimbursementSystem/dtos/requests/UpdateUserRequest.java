package com.revature.reimbursementSystem.dtos.requests;

import com.revature.reimbursementSystem.utils.HashBrowns;

import java.security.NoSuchAlgorithmException;

public class UpdateUserRequest {
    private String currentUsername;
    private String currentPassword;
    private String username;
    private String email;
    private String password1;
    private String password2;
    private String password3;
    private String given_name;
    private String surname;
    private String role_id;
    private boolean is_active;

    private static final HashBrowns hashBrowns = new HashBrowns();
    public UpdateUserRequest(){
        super();
    }

    public UpdateUserRequest(String currentUsername, String currentPassword, String username, String email, String password1, String password2, String given_name, String surname, String role_id, boolean is_active) throws NoSuchAlgorithmException {
        this.currentUsername = currentUsername;
        this.currentPassword = hashBrowns.encryptString(currentPassword);
        this.username = username;
        this.email = email;
        this.password1 = password1;
        this.password2 = password2;
        this.password3 = hashBrowns.encryptString(password1);
        this.given_name = given_name;
        this.surname = surname;
        this.role_id = role_id;
        this.is_active = is_active;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) throws NoSuchAlgorithmException {
        this.currentPassword = hashBrowns.encryptString(currentPassword);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                "currentUsername='" + currentUsername + '\'' +
                ", currentPassword='" + currentPassword + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password1='" + password1 + '\'' +
                ", password2='" + password2 + '\'' +
                ", given_name='" + given_name + '\'' +
                ", surname='" + surname + '\'' +
                ", role_id='" + role_id + '\'' +
                ", is_active=" + is_active +
                '}';
    }
}
