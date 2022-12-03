package com.revature.reimbursementSystem.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursementSystem.daos.TicketDAO;
import com.revature.reimbursementSystem.daos.UserDAO;
import com.revature.reimbursementSystem.handlers.AuthHandler;
import com.revature.reimbursementSystem.handlers.TicketHandler;
import com.revature.reimbursementSystem.handlers.UserHandler;
import com.revature.reimbursementSystem.services.TicketService;
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
        /*Ticket*/
        TicketDAO TIcketDAO = new TicketDAO();
        TicketService ticketService = new TicketService(TIcketDAO);
        TicketHandler ticketHandler = new TicketHandler(ticketService, mapper, tokenService);


        /*handler groups*/
        app.routes(()->{
            //user
            path("/users",() -> {
                post(c -> userhandler.signup(c));
                get(c -> userhandler.getAllUsers(c));
            });
            /*add route to validate users requesting registration*/
            path("/users/update", ()->{
                put(c -> userhandler.updateUser(c));
                get(c -> userhandler.getAllInactiveUsers(c));
            });

            path("/reimbursements",() -> {
                post(c -> ticketHandler.createTicket(c));
                get(c -> ticketHandler.getAllTickets(c));
            });

            path("/reimbursements/update",() -> {
                put(c -> ticketHandler.updateTicket(c));
                get(c -> ticketHandler.getAllPendingTickets(c));
            });



            //auth
            path("/auth", ()->{
                post(c-> authHandler.authenticateUser(c));
            });
        });
    }
}
