package edu.ifmg.libraryManager.services.exceptions;

public class DatabaseException extends RuntimeException{
    public DatabaseException() {
        super();
    }

    public DatabaseException(String message) {
        super(message);
    }
}
