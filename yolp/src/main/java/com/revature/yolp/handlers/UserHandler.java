package com.revature.yolp.handlers;

import com.revature.yolp.services.UserService;
import io.javalin.http.Context;

// purpose of this UserHandler class is to handle http verbs and endpoints
// hierarchy dependency injection -> userhandler -> userservice -> userdao
public class UserHandler {
    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public void signup(Context c) {

    }
}
