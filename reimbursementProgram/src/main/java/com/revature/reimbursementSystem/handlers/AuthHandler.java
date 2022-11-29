package com.revature.reimbursementSystem.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursementSystem.dtos.requests.NewLoginRequest;
import com.revature.reimbursementSystem.dtos.responses.Principal;
import com.revature.reimbursementSystem.models.User;
import com.revature.reimbursementSystem.services.UserService;
import com.revature.reimbursementSystem.utils.customExceptions.InvalidAuthException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


//this class authenticates the user
public class AuthHandler {
    //dependency injections!!!!!!!!
    private final UserService userService;
    private final ObjectMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    public AuthHandler(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    public void authenticateUser(Context ctx) throws IOException {
        NewLoginRequest req = mapper.readValue(ctx.req.getInputStream(), NewLoginRequest.class);
        logger.info(req.toString()+" is attempting to authenticate");
        try{
            Principal principal = userService.login(req);
        }catch(InvalidAuthException e){
            ctx.status(401);
            ctx.json(e);
        }

    }
}
