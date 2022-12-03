package com.revature.reimbursementSystem.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursementSystem.dtos.requests.NewReimbursementRequest;
import com.revature.reimbursementSystem.dtos.requests.UpdateReimbursementRequest;
import com.revature.reimbursementSystem.dtos.responses.Principal;
import com.revature.reimbursementSystem.services.ReimbursementService;
import com.revature.reimbursementSystem.services.TokenService;
import com.revature.reimbursementSystem.utils.customExceptions.InvalidUserException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

//purpose of this handler class is to handle http verbs and endpoints
//hierarchy dependency injection -> user-handler -> user service -> userDAO

public class ReimbursementHandler {

    //dependencies
    private final ReimbursementService reimbursementService;
    private final ObjectMapper mapper;
    private final TokenService tokenService;
    private final static Logger logger = LoggerFactory.getLogger(ReimbursementHandler.class);

    //constructor
    public ReimbursementHandler(ReimbursementService reimbursementService, ObjectMapper mapper, TokenService tokenService) {
        this.reimbursementService = reimbursementService;
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
            NewReimbursementRequest req = mapper.readValue(ctx.req.getInputStream(), NewReimbursementRequest.class);
            reimbursementService.createTicket(req, principal);
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
            TokenService.validateFinanceManagerLogin(token, principal);
            logger.info(principal.getUsername() +" attempting to getAllTickets");
            ctx.status(202);
            ctx.json(reimbursementService.getAllTickets());
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
            ctx.json(reimbursementService.getPendingTickets());
        }catch (InvalidUserException e) {
            ctx.status(403);
            ctx.json(e);
        }catch (Exception e) {
            ctx.json(e);
            ctx.status(400);
        }
    }

    //TODO: CHECK THE TOKEN FOR USER.TYPEID, then if it IS a employee send to overloaded reimbursementService.updateReimbursement
    // for full access to modifying ticket.
    public void updateTicket(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");
            Principal principal = tokenService.extractRequesterDetails(token);
            TokenService.validateFinanceManagerLogin(token, principal);
            logger.info(principal.getUsername() +" attempting to updateReimbursement");
            UpdateReimbursementRequest req = mapper.readValue(ctx.req.getInputStream(), UpdateReimbursementRequest.class);
            ctx.status(202);
            reimbursementService.updateReimbursement(req, principal);
            logger.info("passed handler " + req.toString());
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
