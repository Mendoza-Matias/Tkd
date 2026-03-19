package com.mmendoza.tkd.infrastructure.web.advice;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mmendoza.tkd.core.exception.BussinesException;
import com.mmendoza.tkd.infrastructure.security.exception.AuthException;
import com.mmendoza.tkd.model.Error;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(exception = BussinesException.class)
    public ResponseEntity<Error> handleBsinessException(BussinesException e) {
        Error error = buildError(e, e.getStatus());
        return ResponseEntity.status(e.getStatus()).body(error);
    }

    @ExceptionHandler(exception = AuthException.class)
    public ResponseEntity<Error> handleAuthException(AuthException e) {
        Error error = buildError(e, e.getStatus());
        return ResponseEntity.status(e.getStatus()).body(error);
    }

    @ExceptionHandler(exception = MethodValidationException.class)
    public ResponseEntity<Error> handleMethodValidationException(MethodValidationException e) {
        Error error = buildError(e, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private Error buildError(Exception e, HttpStatus status) {
        Error error = new Error();
        error.setCode(status.value());
        error.setMessage(e.getMessage());
        error.setTimestamp(new Date());
        return error;
    }
}