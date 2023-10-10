package com.student.testing.exception;

public class ResourceAlreadyExistException extends RuntimeException{
    public ResourceAlreadyExistException(String msg){
        super(msg);
    }
}
