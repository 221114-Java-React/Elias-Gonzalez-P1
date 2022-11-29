package com.revature.reimbursementSystem.dtos.responses;

public class Principal {
    private String user_id;
    private String username;
    private boolean is_active;
    private String role_id;


    public Principal(){
        super();
    }

    public Principal(String user_id, String username, boolean is_active, String role_id) {
        this.user_id = user_id;
        this.username = username;
        this.is_active = is_active;
        this.role_id = role_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", is_active=" + is_active +
                ", role_id='" + role_id + '\'' +
                '}';
    }
}
