package com.sartop.demoproductsapi.exception;

public class ClientAlreadyExistsException extends RuntimeException
{
    public ClientAlreadyExistsException(String message)
    {
        super(message);
    }
}
