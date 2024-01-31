package com.billnex.billManage.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException e) {

        return new ResponseEntity(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler({Exception.class})
//    public ResponseEntity handleOtherException(Exception e) {
//        HttpStatus serverErrorCode = HttpStatus.INTERNAL_SERVER_ERROR;
//        if(e instanceof NoResourceFoundException){
//            serverErrorCode = HttpStatus.NOT_FOUND;
//        }
//        return new ResponseEntity(new ExceptionResponse(e.getMessage()),serverErrorCode);
//    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity handleValidationException(MethodArgumentNotValidException ex) {
        Optional<ObjectError> errorMessage = ex.getBindingResult().getAllErrors().stream().findFirst();

        String fieldName = errorMessage
                .filter(error -> error instanceof FieldError)
                .map(error -> ((FieldError) error).getField())
                .orElse("");

        String message = fieldName +" - "+ errorMessage.get().getDefaultMessage();
        return new ResponseEntity<Object>(new ExceptionResponse(message), HttpStatus.BAD_REQUEST);
    }

}
