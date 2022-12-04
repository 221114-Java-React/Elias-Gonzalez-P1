package com.revature.reimbursementSystem.models;

import java.sql.Timestamp;

public class Ticket {
    private String reimb_id, description, payment_id, author_id, resolver_id, status_id, type_id;
    Timestamp submitted;
    Timestamp resolved;
    private String receipt;
    private double amount;

    //new Timestamp(System.currentTimeMillis());


    public Ticket(String reimb_id, String description, String payment_id, String author_id, String resolver_id, String status_id, String type_id, Timestamp submitted, Timestamp resolved, String receipt, double amount) {
        this.reimb_id = reimb_id;
        this.description = description;
        this.payment_id = payment_id;
        this.author_id = author_id;
        this.resolver_id = resolver_id;
        this.status_id = status_id;
        this.type_id = type_id;
        this.submitted = submitted;
        this.resolved = resolved;
        this.receipt = receipt;
        this.amount = amount;
    }


    public String getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(!(description == null || description.isEmpty())){
            this.description = description;
        }
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        if (!(payment_id == null || payment_id.isEmpty())){
            this.payment_id = payment_id;
        }
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getResolver_id() {
        return resolver_id;
    }

    public void setResolver_id(String resolver_id) {
        this.resolver_id = resolver_id;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        if (!(type_id == null || type_id.isEmpty())) {
            this.type_id = type_id;
        }
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public Timestamp getResolved() {
        return resolved;
    }

    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        if(!(receipt == null || receipt.isEmpty())){
            this.receipt = receipt;
        }

    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if(!(amount == 0)){
            this.amount = amount;
        }

    }


    @Override
    public String toString() {
        return "Ticket{" +
                "reimb_id='" + reimb_id + '\'' +
                ", description='" + description + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", author_id='" + author_id + '\'' +
                ", resolver_id='" + resolver_id + '\'' +
                ", status_id='" + status_id + '\'' +
                ", type_id='" + type_id + '\'' +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", receipt='" + receipt + '\'' +
                ", amount=" + amount +
                '}';
    }
}
