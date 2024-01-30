package com.sartop.demoproductsapi.exception;

public class WrongPasswordException extends RuntimeException
{
    public WrongPasswordException(String message)
    {
        super(message);
    }
}
