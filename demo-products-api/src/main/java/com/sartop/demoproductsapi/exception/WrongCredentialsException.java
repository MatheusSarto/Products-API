package com.sartop.demoproductsapi.exception;

public class WrongCredentialsException extends RuntimeException
{
    public WrongCredentialsException(String message)
    {
        super(message);
    }
}
