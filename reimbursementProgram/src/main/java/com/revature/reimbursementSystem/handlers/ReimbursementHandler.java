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

public class ReimbursementHandler {

    private final ReimbursementService reimbursementService;
    private final ObjectMapper mapper;
    private final TokenService tokenService;
    private final static Logger logger = LoggerFactory.getLogger(ReimbursementHandler.class);


    public ReimbursementHandler(ReimbursementService reimbursementService, ObjectMapper mapper, TokenService tokenService) {
        this.reimbursementService = reimbursementService;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    public void createTicket(Context ctx) throws Exception {
        try {
            String token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidUserException("Not signed in");
            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidUserException("Invalid token");
            if(!principal.getIs_active()) throw new InvalidUserException("Check with administrator about account access.");
            logger.info(principal.getUsername() +" attempting to create ticket");
            NewReimbursementRequest req = mapper.readValue(ctx.req.getInputStream(), NewReimbursementRequest.class);
            //send in req and principal to service
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

    public void getAllTickets(Context ctx) throws IOException {
        try {
            String token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidUserException("Not signed in");
            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidUserException("Invalid token");
            if (!principal.getRole_id().equals("1")) throw new InvalidUserException("Not a finance manager");
            if(!principal.getIs_active()) throw new InvalidUserException("Check with administrator about account access.");
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
            if (token == null || token.equals("")) throw new InvalidUserException("Not signed in");
            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidUserException("Invalid token");
            if (!principal.getRole_id().equals("1")) throw new InvalidUserException("Not a finance manager");
            if(!principal.getIs_active()) throw new InvalidUserException("Check with administrator about account access.");
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
            if (token == null || token.equals("")) throw new InvalidUserException("Not signed in");
            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidUserException("Invalid token");
            if (!principal.getRole_id().equals("1")) throw new InvalidUserException("Not a finance manager");
            if(!principal.getIs_active()) throw new InvalidUserException("Check with administrator about account access.");
            logger.info(principal.getUsername() +" attempting to updateReimbursment");
            ctx.status(202);

            UpdateReimbursementRequest req = mapper.readValue(ctx.req.getInputStream(), UpdateReimbursementRequest.class);
            logger.info("passed handler " + req.toString());
            reimbursementService.updateReimbursement(req, principal);

        }catch (InvalidUserException e) {
            ctx.status(403);
            ctx.json(e);
        }catch (Exception e) {
            ctx.json(e);
            ctx.status(400);
        }
    }
}
