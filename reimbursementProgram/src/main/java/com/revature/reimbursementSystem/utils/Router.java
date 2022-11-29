package com.revature.reimbursementSystem.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursementSystem.daos.UserDAO;
import com.revature.reimbursementSystem.handlers.AuthHandler;
import com.revature.reimbursementSystem.handlers.UserHandler;
import com.revature.reimbursementSystem.services.TokenService;
import com.revature.reimbursementSystem.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

//purpose of router classes is to map endpoints
public class Router {
    public static void router(Javalin app){
        /*dependency injections*/
        ObjectMapper mapper = new ObjectMapper();
        JwtConfig jwtConfig = new JwtConfig();
        TokenService tokenService = new TokenService(jwtConfig);
        /*User*/

        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        UserHandler userhandler = new UserHandler(userService, mapper, tokenService);
        AuthHandler authHandler = new AuthHandler(userService, mapper, tokenService);

        /*TODO: Add dependency injections for Reimbursements*/


        /*handler groups*/
        app.routes(()->{
            //user
            path("/users",() -> {
                post(c -> userhandler.signup(c));
                get(c -> userhandler.getAllUsers(c));
            });

            //auth
            path("/auth", ()->{
                post(c-> authHandler.authenticateUser(c));
            });
        });
    }
}
