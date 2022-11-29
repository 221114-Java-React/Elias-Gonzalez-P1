package com.revature.reimbursementSystem.dtos.responses;

public class Principal {
    private String user_id;
    private String username;
    private String role_id;
    private String authorization;


    public Principal(){
        super();
    }

    public Principal(String user_id, String username, String role_id, String authorization) {
        this.user_id = user_id;
        this.username = username;
        this.role_id = role_id;
        this.authorization = authorization;
    }
}
