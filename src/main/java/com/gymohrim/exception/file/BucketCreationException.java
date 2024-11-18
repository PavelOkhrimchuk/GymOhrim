package com.gymohrim.exception.file;

public class BucketCreationException extends RuntimeException{

    public BucketCreationException(String message) {
        super(message);
    }

    public BucketCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}