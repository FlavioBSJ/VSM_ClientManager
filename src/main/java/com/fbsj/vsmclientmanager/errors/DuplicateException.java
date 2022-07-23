package com.fbsj.vsmclientmanager.errors;

public class DuplicateException extends RuntimeException{
    private static final long serialVersionUID = -7127855501885350491L;

    private static final String MESSAGE = "O recurso informado já existe.";

    public DuplicateException() {
        super(MESSAGE);
    }
}
