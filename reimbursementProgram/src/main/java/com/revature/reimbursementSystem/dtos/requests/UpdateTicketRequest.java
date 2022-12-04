package com.revature.reimbursementSystem.dtos.requests;

public class UpdateTicketRequest {
    String reimb_id;
    String status_id;
    double amount;
    String description;
    String receipt;
    String payment_id;
    String type_id;

    public UpdateTicketRequest(){
        super();
    }


    public UpdateTicketRequest(String reimb_id, String status_id, double amount, String description, String receipt, String payment_id, String type_id) {
        this.reimb_id = reimb_id;
        this.status_id = status_id;
        this.amount = amount;
        this.description = description;
        this.receipt = receipt;
        this.payment_id = payment_id;
        this.type_id = type_id;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return "UpdateTicketRequest{" +
                "reimb_id='" + reimb_id + '\'' +
                ", status_id='" + status_id + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", receipt='" + receipt + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", type_id='" + type_id + '\'' +
                '}';
    }

    //    public UpdateTicketRequest() {
//        super();
//    }
//
//    public UpdateTicketRequest(String reimb_id, String status_id) {
//        this.reimb_id = reimb_id;
//        this.status_id = status_id;
//    }
//
//    public String getReimb_id() {
//        return reimb_id;
//    }
//
//    public void setReimb_id(String reimb_id) {
//        this.reimb_id = reimb_id;
//    }
//
//    public String getStatus_id() {
//        return status_id;
//    }
//
//    public void setStatus_id(String status_id) {
//        this.status_id = status_id;
//    }
//
//
//    @Override
//    public String toString() {
//        return "UpdateTicketRequest{" +
//                "reimb_id='" + reimb_id + '\'' +
//                ", status_id='" + status_id + '\'' +
//                '}';
//    }
}
