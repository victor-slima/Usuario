package com.victor.usuario.infraestructure.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
    public ConflictException(String message, Throwable cause) {super(message, cause);}
}
