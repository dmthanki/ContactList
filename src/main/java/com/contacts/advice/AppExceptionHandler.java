package com.contacts.advice;

import com.contacts.exception.ContactNotFoundException;
import com.contacts.exception.CustomUniqueConstrainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException e){
        Map<String,String> err = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            err.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        return err;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String noContentException(HttpMessageNotReadableException e){
        return "No Body Provided";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ContactNotFoundException.class)
    public Map<String,String> handleException(ContactNotFoundException uno){
        Map<String,String> emap = new HashMap<>();
        emap.put("errorMessage",uno.getMessage());
        return emap;
    }

    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @ExceptionHandler(CustomUniqueConstrainException.class)
    public Map<String,String> handleUniqeException(CustomUniqueConstrainException e){
        Map<String,String> exmap = new HashMap<>();
        exmap.put("errorMessage",e.getMessage());
        return exmap;
    }
}