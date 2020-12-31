/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peter.exceptions;

/**
 *
 * @author 19127639
 */
public class FatalException extends Exception
{

    public FatalException(String message,Throwable cause)
    {
        super(message,cause);
    }
    
}
