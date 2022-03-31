package com.ensta.librarymanager.exception;

public class ServiceException extends Throwable {
    private static final long serialVersionUID = 1L; // Empêcher Eclipse de mettre un warning

    public ServiceException() {
        super("Une erreur s'est produite dans la Service");
    }

}
