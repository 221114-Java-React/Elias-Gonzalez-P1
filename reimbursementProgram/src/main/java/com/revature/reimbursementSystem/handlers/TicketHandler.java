package com.revature.reimbursementSystem.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursementSystem.dtos.requests.NewTicketRequest;
import com.revature.reimbursementSystem.dtos.requests.UpdateTicketRequest;
import com.revature.reimbursementSystem.dtos.responses.Principal;
import com.revature.reimbursementSystem.services.TicketService;
import com.revature.reimbursementSystem.services.TokenService;
import com.revature.reimbursementSystem.utils.customExceptions.InvalidUserException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

//purpose of this handler class is to handle http verbs and endpoints
//hierarchy dependency injection -> user-handler -> user service -> userDAO

public class TicketHandler {

    //dependencies
    private final TicketService ticketService;
    private final ObjectMapper mapper;
    private final TokenService tokenService;
    private final static Logger logger = LoggerFactory.getLogger(TicketHandler.class);

    //constructor
    public TicketHandler(TicketService ticketService, ObjectMapper mapper, TokenService tokenService) {
        this.ticketService = ticketService;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    //DEFAULT USER HANDLERS
    public void createTicket(Context ctx) throws Exception {
        try {
            String token = ctx.req.getHeader("authorization");
            Principal principal = tokenService.extractRequesterDetails(token);
            TokenService.validateEmployeeLogin(token, principal);
            logger.info(principal.getUsername() +" attempting to createTicket");
            NewTicketRequest req = mapper.readValue(ctx.req.getInputStream(), NewTicketRequest.class);
            ticketService.createTicket(req, principal);
            ctx.status(201);
            ctx.json(req);
        }catch (InvalidUserException e) {
            ctx.status(403);
            ctx.json(e);
        }catch (Exception e) {
            ctx.json(e);
            ctx.status(400);
        }
    }

    //FINANCE MANAGER HANDLERS
    public void getAllTickets(Context ctx) throws IOException {
        try {
            String token = ctx.req.getHeader("authorization");
            Principal principal = tokenService.extractRequesterDetails(token);
            TokenService.validateEmployeeLogin(token, principal);
            String msg = principal.getRole_id().equals("1") ? " attempting to getAllTickets" : " attempting to usersTickets";
            logger.info(principal.getUsername() + msg);
            ctx.status(202);
            ctx.json(ticketService.getAllTickets(principal));
        }catch (InvalidUserException e) {
            ctx.status(403);
            ctx.json(e);
        }catch (Exception e) {
            ctx.json(e);
            ctx.status(400);
        }
    }

    public void getAllPendingTickets(Context ctx) throws IOException {
        try {
            String token = ctx.req.getHeader("authorization");
            Principal principal = tokenService.extractRequesterDetails(token);
            TokenService.validateFinanceManagerLogin(token, principal);
            logger.info(principal.getUsername() +" attempting to getAllPendingTickets");
            ctx.status(202);
            ctx.json(ticketService.getPendingTickets());
        }catch (InvalidUserException e) {
            ctx.status(403);
            ctx.json(e);
        }catch (Exception e) {
            ctx.json(e);
            ctx.status(400);
        }
    }

    public void updateTicket(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");
            Principal principal = tokenService.extractRequesterDetails(token);
            //only users with access to tickets are allowed through
            TokenService.validateReimbursementAccessLogin(token, principal);
            logger.info(principal.getUsername() +" attempting to updateReimbursement");
            UpdateTicketRequest req = mapper.readValue(ctx.req.getInputStream(), UpdateTicketRequest.class);
            ctx.json(ticketService.updateTicket(req, principal));
            ctx.status(202);
        }catch (InvalidUserException e) {
            ctx.status(403);
            ctx.json(e);
        }catch (Exception e) {
            ctx.json(e);
            ctx.status(400);
        }
    }

    //ADMIN ONLY HANDLERS


}
