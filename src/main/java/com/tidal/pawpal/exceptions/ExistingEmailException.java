package com.tidal.pawpal.exceptions;

public class ExistingEmailException extends RuntimeException {

    public ExistingEmailException(String message) {
        super(message);
    }

    public ExistingEmailException(Throwable cause) {
        super(cause);
    }

    public ExistingEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistingEmailException() {
        super("Errore: email già in uso");
    }

}
