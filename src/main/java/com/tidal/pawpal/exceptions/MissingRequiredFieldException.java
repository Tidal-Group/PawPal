package com.tidal.pawpal.exceptions;

public class MissingRequiredFieldException extends RuntimeException {

    public MissingRequiredFieldException(String message) {
        super(message);
    }

    public MissingRequiredFieldException(Throwable cause) {
        super(cause);
    }

    public MissingRequiredFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingRequiredFieldException() {
        super("Errore: non è stato inserito un campo required");
    }

}
