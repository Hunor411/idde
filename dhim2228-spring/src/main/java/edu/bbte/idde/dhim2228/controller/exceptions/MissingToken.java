package edu.bbte.idde.dhim2228.controller.exceptions;

public class MissingToken extends RuntimeException {
    public MissingToken(String message) {
        super(message);
    }
}
