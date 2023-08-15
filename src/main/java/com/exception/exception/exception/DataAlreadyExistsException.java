package com.exception.exception.exception;

import lombok.Getter;

@Getter
public class DataAlreadyExistsException extends RuntimeException{
    public String message;
    public DataAlreadyExistsException(String message)
    {
        super(message);
        this.message=message;
    }

}
