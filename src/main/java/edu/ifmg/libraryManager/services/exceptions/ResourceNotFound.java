package edu.ifmg.libraryManager.services.exceptions;

public class ResourceNotFound extends RuntimeException{
    public ResourceNotFound() {
        super("Resource Not Found");
    }
    public ResourceNotFound(String message) {
        super(message);
    }
}
