package com.student.testing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<Map> resourceNotFoundEx(ResourceNotFound ex){
        HashMap<String,String> hm=new HashMap<>();
        hm.put("message",ex.getMessage());
        return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<Map> resourceFoundEx(ResourceAlreadyExistException ex){
        HashMap<String,String> hm=new HashMap<>();
        hm.put("message",ex.getMessage());
        return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
    }
}
