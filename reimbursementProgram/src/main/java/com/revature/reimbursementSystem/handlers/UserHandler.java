package com.revature.reimbursementSystem.handlers;

import com.revature.reimbursementSystem.services.UserService;
import io.javalin.http.Context;
//purpose of this handler class is to handle http verbs and endpoints
//hierarchy dependency injection -> user-handler -> user service -> userDAO

public class UserHandler {
    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public void signup(Context c){
    }
}
