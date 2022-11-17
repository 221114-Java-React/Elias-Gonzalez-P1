public class Ticket {
    //constructors
    public Ticket(double amount, String description, String type) {
    this.amount = amount;
    this.description = description;
    this.status = "Pending";
    this.type = type;
    System.out.println("Ticket created with " + amount + " amount for " + description);
}
    //variables
    private double amount;
    private String description;
    private String status;
    private String type;
    
    //setters and getters
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        // Override toString
        return "Expense for " + amount + " for " + description +". Status: " + status+". Type: " + type;
    }
}
