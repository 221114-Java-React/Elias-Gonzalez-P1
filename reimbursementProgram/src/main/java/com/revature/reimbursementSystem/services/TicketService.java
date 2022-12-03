package com.revature.reimbursementSystem.services;

import com.revature.reimbursementSystem.daos.TicketDAO;
import com.revature.reimbursementSystem.dtos.requests.NewTicketRequest;
import com.revature.reimbursementSystem.dtos.requests.UpdateTicketRequest;
import com.revature.reimbursementSystem.dtos.responses.Principal;
import com.revature.reimbursementSystem.models.Ticket;
import com.revature.reimbursementSystem.utils.customExceptions.InvalidTicketException;
import com.revature.reimbursementSystem.utils.customExceptions.InvalidUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TicketService {
    private final String[] types = {"0", "1", "2", "3"};
    List<String> typeList = Arrays.asList(types);

    //dependency
    private final TicketDAO TIcketDAO;
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);
    //constructor
    public TicketService(TicketDAO TIcketDAO) {
        this.TIcketDAO = TIcketDAO;
    }


    //method
    public void createTicket(NewTicketRequest req, Principal principal) {
        if (!isValidDescription(req))throw new InvalidTicketException("Invalid description");
        if (!isValidAmount(req))throw new InvalidTicketException("Invalid amount");
        if (!isValidType(req))throw new InvalidTicketException("Invalid type (choose 0-3)");
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Ticket createdTicket = new Ticket(UUID.randomUUID().toString(), req.getDescription(), req.getPayment_id(), principal.getUser_id(), null, "0", req.getType_id(), currentTimestamp, null, req.getReceipt(), req.getAmount());
        logger.info(createdTicket.toString());
        TIcketDAO.save(createdTicket);
    }



    //helper methods
    private boolean isValidAmount(NewTicketRequest req){
        return req.getAmount() != 0;
    }
    private boolean isValidDescription(NewTicketRequest req){
        return !req.getDescription().equals("");
    }
    private boolean isValidType(NewTicketRequest req){return !req.getType_id().equals("") && typeList.contains(req.getType_id());
    }


    //todo add if statement that checks principal for role_id and passes it to
    public void updateTicket(UpdateTicketRequest req, Principal principal) {
        List<Ticket> allTickets = getAllTickets(principal);
        List<String> ticketIds = allTicketsToAllIds(allTickets);
        Ticket currentTicket = TIcketDAO.findByReimb_id(req.getReimb_id());
        updateTicketValidation(req, ticketIds, currentTicket);

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        currentTicket.setResolved(currentTimestamp);
        currentTicket.setResolver_id(principal.getUser_id());
        currentTicket.setStatus_id(req.getStatus_id());
        logger.info("Ticket passed to TicketDAO");
        TIcketDAO.update(currentTicket);
    }


    public List<Ticket> getAllTickets() {return TIcketDAO.findAll();}

    public List<Ticket> getAllTickets(Principal principal) {
        if (principal.getRole_id().equals("0")){
            return TIcketDAO.getAllTicketsByUserId(principal);
        } else if (principal.getRole_id().equals("2")) {
            throw new InvalidUserException("Administrators cannot access tickets");
        }
        return TIcketDAO.findAll();}

    public List<String> getAllTicketIds() {return TIcketDAO.findAllIds();}
    public List<Ticket> getPendingTickets() {return TIcketDAO.getAllPendingTickets();}


    //helper methods
    public void updateTicketValidation(UpdateTicketRequest req, List<String> ticketIds, Ticket currentTicket) {
        if(req.getReimb_id().isEmpty()||req.getReimb_id()==null)throw new InvalidTicketException("Invalid Ticket ID");
        if(req.getStatus_id().equals("0"))throw new InvalidTicketException("Ticket cannot be set to pending");
        if(!ticketIds.contains(req.getReimb_id()))throw new InvalidTicketException("Invalid Ticket ID");
        if (!currentTicket.getStatus_id().equals("0"))throw new InvalidTicketException("Ticket has already been resolved");
    }

    public List<String> allTicketsToAllIds(List<Ticket> allTickets){
        List<String> allIds = new ArrayList<String>();
        for (Ticket ticket: allTickets){
            allIds.add(ticket.getReimb_id());
        }
        return allIds;
    }


}
