package com.gymohrim.exception.user;

public class InvalidDateOrUserException extends RuntimeException{

    public InvalidDateOrUserException(String message) {
        super(message);
    }
}
