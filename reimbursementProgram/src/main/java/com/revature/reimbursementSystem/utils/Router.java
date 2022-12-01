package com.revature.reimbursementSystem.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursementSystem.daos.ReimbursementDAO;
import com.revature.reimbursementSystem.daos.UserDAO;
import com.revature.reimbursementSystem.handlers.AuthHandler;
import com.revature.reimbursementSystem.handlers.ReimbursementHandler;
import com.revature.reimbursementSystem.handlers.UserHandler;
import com.revature.reimbursementSystem.services.ReimbursementService;
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
        /*Reimbursement*/
        ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
        ReimbursementService reimbursementService = new ReimbursementService(reimbursementDAO);
        ReimbursementHandler reimbursementHandler = new ReimbursementHandler(reimbursementService, mapper, tokenService);


        /*handler groups*/
        app.routes(()->{
            //user
            path("/users",() -> {
                post(c -> userhandler.signup(c));
                get(c -> userhandler.getAllUsers(c));
            });
            /*add route to validate users requesting registration*/
            path("/users/update", ()->{
                post(c -> userhandler.updateUser(c));
                get(c -> userhandler.getAllInactiveUsers(c));
            });

            path("/reimbursements",() -> {
                post(c -> reimbursementHandler.createTicket(c));
                get(c -> reimbursementHandler.getAllTickets(c));
            });




            //auth
            path("/auth", ()->{
                post(c-> authHandler.authenticateUser(c));
            });
        });
    }
}
