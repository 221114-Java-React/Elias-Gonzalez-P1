package com.revature.reimbursementSystem;

import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class MainDriver {
    public static void main(String[] args) {
        int port = 8080;
        Javalin app = Javalin.create(c -> {
            c.contextPath = "/reimbursementSystem";
        }).start(port);
    }
}
