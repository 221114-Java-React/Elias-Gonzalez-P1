package com.revature.reimbursementSystem.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursementSystem.dtos.requests.NewUserRequest;
import com.revature.reimbursementSystem.services.UserService;
import com.revature.reimbursementSystem.utils.customExceptions.InvalidUserException;
import io.javalin.http.Context;

import java.io.IOException;
//purpose of this handler class is to handle http verbs and endpoints
//hierarchy dependency injection -> user-handler -> user service -> userDAO

public class UserHandler {
    private final UserService userService;
    private final ObjectMapper mapper;

    public UserHandler(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    public void signup(Context c) throws IOException {
        NewUserRequest req = mapper.readValue(c.req.getInputStream(), NewUserRequest.class);
        try {
            userService.saveUser(req);
            c.status(201);
        }catch (InvalidUserException e){
            c.status(403);
            c.json(e);
        }

    }
}
