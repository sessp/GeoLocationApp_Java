package com.peter.exceptions;

public class InvalidArgException extends Exception
{

    public InvalidArgException(String message,Throwable cause)
    {
        super(message,cause);
    }

}
