package com.revature.reimbursementSystem.dtos.requests;

public class UpdateTicketRequest {
    String reimb_id;
    String status_id;

    public UpdateTicketRequest() {
        super();
    }

    public UpdateTicketRequest(String reimb_id, String status_id) {
        this.reimb_id = reimb_id;
        this.status_id = status_id;
    }

    public String getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }


    @Override
    public String toString() {
        return "UpdateTicketRequest{" +
                "reimb_id='" + reimb_id + '\'' +
                ", status_id='" + status_id + '\'' +
                '}';
    }
}
