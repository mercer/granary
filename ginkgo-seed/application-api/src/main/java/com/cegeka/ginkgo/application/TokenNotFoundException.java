package com.cegeka.ginkgo.application;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(String message){
        super(message);
    }
}
