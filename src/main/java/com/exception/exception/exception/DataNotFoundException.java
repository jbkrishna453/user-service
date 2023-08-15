package com.exception.exception.exception;

import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException{
    public String message;
    public DataNotFoundException(String message)
    {
        super(message);
        this.message=message;
    }
}
