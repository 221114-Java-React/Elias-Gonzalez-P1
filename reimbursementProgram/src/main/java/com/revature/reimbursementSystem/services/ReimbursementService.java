package com.revature.reimbursementSystem.services;

import com.revature.reimbursementSystem.daos.ReimbursementDAO;
import com.revature.reimbursementSystem.dtos.requests.NewReimbursementRequest;
import com.revature.reimbursementSystem.dtos.requests.UpdateReimbursementRequest;
import com.revature.reimbursementSystem.dtos.responses.Principal;
import com.revature.reimbursementSystem.models.Reimbursement;
import com.revature.reimbursementSystem.utils.customExceptions.InvalidTicketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ReimbursementService {
    private final String[] types = {"0", "1", "2", "3"};
    List<String> typeList = Arrays.asList(types);

    //dependency
    private final ReimbursementDAO reimbursementDAO;
    private static final Logger logger = LoggerFactory.getLogger(ReimbursementService.class);
    //constructor
    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.reimbursementDAO = reimbursementDAO;
    }


    //method
    public void createTicket(NewReimbursementRequest req, Principal principal) {
        if (!isValidDescription(req))throw new InvalidTicketException("Invalid description");
        if (!isValidAmount(req))throw new InvalidTicketException("Invalid amount");
        if (!isValidType(req))throw new InvalidTicketException("Invalid type (choose 0-3)");
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Reimbursement createdTicket = new Reimbursement(UUID.randomUUID().toString(), req.getDescription(), req.getPayment_id(), principal.getUser_id(), null, "0", req.getType_id(), currentTimestamp, null, req.getReceipt(), req.getAmount());
        logger.info(createdTicket.toString());
        reimbursementDAO.save(createdTicket);
    }



    //helper methods
    private boolean isValidAmount(NewReimbursementRequest req){
        return req.getAmount() != 0;
    }
    private boolean isValidDescription(NewReimbursementRequest req){
        return !req.getDescription().equals("");
    }
    private boolean isValidType(NewReimbursementRequest req){return !req.getType_id().equals("") && typeList.contains(req.getType_id());
    }


    //TODO: figure out how to overload this method with a principal to check if it is a USER UPDATING THEIR OWN TICKET
    public void updateReimbursement(UpdateReimbursementRequest req) {
        List<String> tickets = getAllTicketIds();
        Reimbursement currentTicket = reimbursementDAO.findByReimb_id(req.getReimb_id());
        if(req.getReimb_id().isEmpty()||req.getReimb_id()==null)throw new InvalidTicketException("Invalid Reimbursement ID");
        if(req.getStatus_id().equals("0"))throw new InvalidTicketException("Ticket cannot be set to pending");
        if(!tickets.contains(req.getReimb_id()))throw new InvalidTicketException("Invalid Reimbursement ID");
        if (!currentTicket.getStatus_id().equals("0"))throw new InvalidTicketException("Ticket has already been resolved");
        logger.info("Reimbursement passed to reimbursementDAO");
        reimbursementDAO.update(req);
    }


    public List<Reimbursement> getAllTickets() {return reimbursementDAO.findAll();}
    public List<String> getAllTicketIds() {return reimbursementDAO.findAllIds();}
    public List<Reimbursement> getPendingTickets() {return reimbursementDAO.getAllPendingReimbursements();}
}
