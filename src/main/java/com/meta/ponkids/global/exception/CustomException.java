package com.meta.ponkids.global.exception;

public class CustomException extends RuntimeException{

    private String redirectUrl;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, String redirectUrl) {
        super(message);
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
