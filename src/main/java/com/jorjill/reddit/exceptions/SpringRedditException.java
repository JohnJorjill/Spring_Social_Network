package com.jorjill.reddit.exceptions;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String exMessage){
        super(exMessage);
    }
}
