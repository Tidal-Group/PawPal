package com.tidal.pawpal.exceptions;

public class ExistingUsernameException extends RuntimeException {

    public ExistingUsernameException(String message) {
        super(message);
    }

    public ExistingUsernameException(Throwable cause) {
        super(cause);
    }

    public ExistingUsernameException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistingUsernameException() {
        super("Errore: username già in uso");
    }

}
