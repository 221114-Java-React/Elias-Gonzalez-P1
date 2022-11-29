package com.revature.reimbursementSystem.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursementSystem.dtos.requests.NewLoginRequest;
import com.revature.reimbursementSystem.dtos.responses.Principal;
import com.revature.reimbursementSystem.models.User;
import com.revature.reimbursementSystem.services.TokenService;
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
    private final TokenService tokenService;
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    public AuthHandler(UserService userService, ObjectMapper mapper, TokenService tokenService) {
        this.userService = userService;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    public void authenticateUser(Context ctx) throws IOException {
        NewLoginRequest req = mapper.readValue(ctx.req.getInputStream(), NewLoginRequest.class);
        logger.info(req.toString()+" is attempting to authenticate");
        try{
            //userService.login(req) returns the principal after confirming with dao that the user exists
            Principal principal = userService.login(req);
            //tokenService generates a token based off of the principal from .login() method
            String token = tokenService.generateToken(principal);
            //we respond with the token as header and principal json as body
            ctx.res.setHeader("authorization", token);
            ctx.json(principal);
            ctx.status(202);
            logger.info("LOGIN SUCCESS : "+principal.toString());
        }catch(InvalidAuthException e){
            ctx.status(401);
            ctx.json(e);
        }

    }
}
