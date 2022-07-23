package com.fbsj.vsmclientmanager.errors;

public class ClientNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -7127855501885350491L;

    private static final String MESSAGE = "O cliente informado nao existe.";

    public ClientNotFoundException() {
        super(MESSAGE);
    }
}