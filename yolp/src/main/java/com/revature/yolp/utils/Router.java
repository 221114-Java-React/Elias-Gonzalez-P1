package com.revature.yolp.utils;

import com.revature.yolp.daos.UserDAO;
import com.revature.yolp.handlers.UserHandler;
import com.revature.yolp.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

//router class maps endpoints
public class Router {
    public static void router(Javalin app) {
        // User
        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        UserHandler userHandler = new UserHandler(userService);

        // Restaurant

        //Review

        // handler groups
        app.routes(() -> {
            path("/users", () -> {
                post(c -> userHandler.signup(c));
            });
        });
    }
}
