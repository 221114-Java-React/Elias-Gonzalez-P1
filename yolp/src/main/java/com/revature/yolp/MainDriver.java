package com.revature.yolp;


import com.revature.yolp.utils.Router;
import io.javalin.Javalin;

public final class MainDriver {
    public static void main(String[] args) {

        System.out.println("Hello World!");
        int yolpPort = 8080;
        Javalin app = Javalin.create(ctx -> {
            ctx.contextPath = "/yolp";
            }).start(yolpPort);

        Router.router(app);

    }
}
