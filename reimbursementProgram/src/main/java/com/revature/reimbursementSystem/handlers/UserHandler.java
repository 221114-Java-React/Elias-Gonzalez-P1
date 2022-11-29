package com.revature.reimbursementSystem.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursementSystem.dtos.requests.NewUserRequest;
import com.revature.reimbursementSystem.dtos.responses.Principal;
import com.revature.reimbursementSystem.models.User;
import com.revature.reimbursementSystem.services.TokenService;
import com.revature.reimbursementSystem.services.UserService;
import com.revature.reimbursementSystem.utils.customExceptions.InvalidUserException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
//purpose of this handler class is to handle http verbs and endpoints
//hierarchy dependency injection -> user-handler -> user service -> userDAO

public class UserHandler {
    private final UserService userService;
    private final ObjectMapper mapper;
    private final TokenService tokenService;
    private final static Logger logger = LoggerFactory.getLogger(User.class);

    public UserHandler(UserService userService, ObjectMapper mapper, TokenService tokenService) {
        this.userService = userService;
        this.mapper = mapper;
        this.tokenService = tokenService;
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

    public void getAllUsers(Context ctx) {
       try {
           String token = ctx.req.getHeader("authorization");
           if (token == null || token.equals("")) throw new InvalidUserException("Not signed in");
           Principal principal = tokenService.extractRequesterDetails(token);
           if (principal == null) throw new InvalidUserException("Invalid token");
           if (!principal.getRole_id().equals("2")) throw new InvalidUserException("Not an administrator");
           //add an IS ACTIVE check for getAll() users.


           logger.info(principal.toString());
           ctx.json(userService.getAllUsers());
       }catch (InvalidUserException e){
           ctx.json(e);
           ctx.status(401);
       }
    }
}
