package com.revature.reimbursementSystem.dtos.requests;

public class NewReimbursementRequest {
    String description;
    double amount;
    String receipt;
    String payment_id;
    String type_id;

    public NewReimbursementRequest(){
        super();
    }

    public NewReimbursementRequest(String description, double amount, String receipt, String payment_id, String type_id) {
        this.description = description;
        this.amount = amount;
        this.receipt = receipt;
        this.payment_id = payment_id;
        this.type_id = type_id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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
        return "NewReimbursementRequest{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                ", receipt='" + receipt + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", type_id='" + type_id + '\'' +
                '}';
    }
}
