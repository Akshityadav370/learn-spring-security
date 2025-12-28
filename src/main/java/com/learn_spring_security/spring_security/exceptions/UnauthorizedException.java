package com.learn_spring_security.spring_security.exceptions;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String message) {
        super(message);
    }
}

