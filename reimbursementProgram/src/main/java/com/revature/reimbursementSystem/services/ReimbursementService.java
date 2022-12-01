package com.revature.reimbursementSystem.services;

import com.revature.reimbursementSystem.daos.ReimbursementDAO;
import com.revature.reimbursementSystem.dtos.requests.NewReimbursementRequest;
import com.revature.reimbursementSystem.dtos.responses.Principal;

public class ReimbursementService {
    private final ReimbursementDAO reimbursementDAO;

    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.reimbursementDAO = reimbursementDAO;
    }


    public void createTicket(NewReimbursementRequest req, Principal principal) {

    }
}
