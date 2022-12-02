package com.revature.reimbursementSystem.utils.customExceptions;

public class InvalidTicketException extends RuntimeException {
    public InvalidTicketException() {
        super();
    }

    public InvalidTicketException(String message) {
        super(message);
    }

    public InvalidTicketException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTicketException(Throwable cause) {
        super(cause);
    }

    protected InvalidTicketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
