package com.sartop.demoproductsapi.exception;

public class EntityNotFoundException extends RuntimeException
{
    public EntityNotFoundException(String message)
    {
        super(message);
    }
}
