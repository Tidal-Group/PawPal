package com.tidal.pawpal.exceptions;

public class AuthenticationFailureException extends RuntimeException {

    public AuthenticationFailureException(String message) {
        super(message);
    }

    public AuthenticationFailureException(Throwable cause) {
        super(cause);
    }

    public AuthenticationFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationFailureException() {
        super("Errore: autenticazione fallita");
    }

}
