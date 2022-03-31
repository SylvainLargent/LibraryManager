package com.ensta.librarymanager.exception;

public class DaoException extends Exception {

    private static final long serialVersionUID = 1L; // Empêcher Eclipse de mettre un warning

    public DaoException() {
        super("Une erreur s'est produite dans la DAO");
    }
}
