package com.praxy.moviecatalogservice.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handleException(Exception ex){
        log.error("exception Occured: " + ex);
        return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
    }
}
