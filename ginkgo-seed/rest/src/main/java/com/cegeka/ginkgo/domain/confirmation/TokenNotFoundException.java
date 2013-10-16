package com.cegeka.ginkgo.domain.confirmation;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(String message){
        super(message);
    }
}
