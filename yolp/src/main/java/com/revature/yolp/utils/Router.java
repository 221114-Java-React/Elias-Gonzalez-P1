package com.revature.yolp.utils;


import com.revature.yolp.handlers.UserHandler;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class Router {
    //purpose of router class is to map endpoints
    public static void router(Javalin app){
        //user
        UserHandler userHandler = new UserHandler();
        app.routes(() ->{
            //handler groups
            path("/users", ()->{
                post(c-> userHandler.signup(c));

            });
        });
    }
}
