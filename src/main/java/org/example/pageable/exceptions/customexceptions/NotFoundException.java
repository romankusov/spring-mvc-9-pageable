package org.example.pageable.exceptions.customexceptions;

public class NotFoundException extends RuntimeException
{
    public NotFoundException(String message) {
        super(message);
    }
}
