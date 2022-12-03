package com.revature.reimbursementSystem.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursementSystem.dtos.requests.NewUserRequest;
import com.revature.reimbursementSystem.dtos.requests.UpdateUserRequest;
import com.revature.reimbursementSystem.dtos.responses.Principal;
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
    //dependencies
    private final UserService userService;
    private final ObjectMapper mapper;
    private final TokenService tokenService;
    private final static Logger logger = LoggerFactory.getLogger(UserHandler.class);

    //constructor
    public UserHandler(UserService userService, ObjectMapper mapper, TokenService tokenService) {
        this.userService = userService;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    //SIGN UP HANDLER
    public void signup(Context ctx) throws IOException {
        NewUserRequest req = mapper.readValue(ctx.req.getInputStream(), NewUserRequest.class);
        try {
            userService.saveUser(req);
            ctx.status(201);
        } catch (InvalidUserException e) {
            ctx.status(403);
            ctx.json(e);
        }
    }

    //DEFAULT USER HANDLERS

    //FINANCE MANAGER HANDLERS

    //ADMIN ONLY HANDLERS
    public void getAllUsers(Context ctx) {
        try {

            String token = ctx.req.getHeader("authorization");
            Principal principal = tokenService.extractRequesterDetails(token);
            TokenService.validateAdministratorLogin(token, principal);
            ctx.json(userService.getAllUsers());
            logger.info(principal.getUsername() +": getAllUsers passed handler. ");
        } catch (InvalidUserException e) {
            ctx.json(e);
            ctx.status(401);
        }
    }

    public void getAllInactiveUsers(Context ctx){
        try {
            String token = ctx.req.getHeader("authorization");
            Principal principal = tokenService.extractRequesterDetails(token);
            TokenService.validateAdministratorLogin(token, principal);
            ctx.json(userService.getAllInactiveUsers());
            logger.info(principal.getUsername() +": getAllUsers SUCCESS");
        } catch (InvalidUserException e) {
            ctx.json(e);
            ctx.status(401);
        }
    }

    public void updateUser(Context ctx) throws Exception {
        try {
            String token = ctx.req.getHeader("authorization");
            Principal principal = tokenService.extractRequesterDetails(token);
            TokenService.validateAdministratorLogin(token, principal);
            UpdateUserRequest req = mapper.readValue(ctx.req.getInputStream(), UpdateUserRequest.class);
            logger.info(principal.getUsername()+" attempting to update with "+req.toString());
            ctx.json(req);
            userService.updateUser(req);
        }catch (InvalidUserException e) {
            ctx.json(e);
            ctx.status(401);
        } catch (Exception e) {
            ctx.json(e);
            ctx.status(400);
        }
    }
}

