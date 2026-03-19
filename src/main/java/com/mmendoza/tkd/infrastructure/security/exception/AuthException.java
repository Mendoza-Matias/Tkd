package com.mmendoza.tkd.infrastructure.security.exception;

import org.springframework.http.HttpStatus;

import com.mmendoza.tkd.core.exception.BussinesException;

public class AuthException extends BussinesException {

    public AuthException(String message, HttpStatus status) {
        super(message, status);
    }

}
