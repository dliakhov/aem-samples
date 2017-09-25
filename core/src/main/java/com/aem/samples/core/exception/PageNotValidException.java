package com.aem.samples.core.exception;

public class PageNotValidException extends RuntimeException {

    public PageNotValidException() {
    }

    public PageNotValidException(String message) {
        super(message);
    }

}
