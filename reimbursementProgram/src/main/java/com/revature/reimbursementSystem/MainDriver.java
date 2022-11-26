package com.revature.reimbursementSystem;

import com.revature.reimbursementSystem.utils.Router;
import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class MainDriver {
    public static void main(String[] args) {
        //port number
        int port = 8080;
        //instance of javalin to make endpoint "reimbursementSystem"
        Javalin app = Javalin.create(c -> {
            c.contextPath = "/reimbursementSystem";
        }).start(port);
        /*router takes the Javalin object "app" and its endpoint
        "/reimbursementSystem" and maps more endpoints.*/
        Router.router(app);
    }
}
