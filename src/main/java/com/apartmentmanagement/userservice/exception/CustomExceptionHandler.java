package com.apartmentmanagement.userservice.exception;

import com.apartmentmanagement.userservice.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler{

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFountException(DataNotFoundException dataNotFoundException)
    {
        ErrorResponse errorResponse=new ErrorResponse(dataNotFoundException.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.OK);
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleDataAlreadyException(DataAlreadyExistsException dataAlreadyExistsException)
    {
        ErrorResponse errorResponse=new ErrorResponse(dataAlreadyExistsException.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception){
        System.out.println(exception);
        return new ResponseEntity<>(new ErrorResponse("internal error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
