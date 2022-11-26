package com.revature.reimbursementSystem.utils;

import com.revature.reimbursementSystem.daos.UserDAO;
import com.revature.reimbursementSystem.handlers.UserHandler;
import com.revature.reimbursementSystem.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

//purpose of router classes is to map endpoints
public class Router {
    public static void router(Javalin app){
        /*dependency injections*/
        /*User*/
        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        UserHandler userhandler = new UserHandler(userService);
        /*TODO: Add dependency injections for Reimbursements*/


        /*handler groups*/
        app.routes(()->{
            path("/users",() -> {
                post(c -> userhandler.signup(c));
            });
        });
    }
}
