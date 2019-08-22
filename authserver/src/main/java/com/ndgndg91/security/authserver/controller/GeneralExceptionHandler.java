package com.ndgndg91.security.authserver.controller;

import com.ndgndg91.security.authserver.model.api.response.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import static com.ndgndg91.security.authserver.model.api.response.ApiResult.ERROR;

@ControllerAdvice
public class GeneralExceptionHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    private ResponseEntity<ApiResult> newResponse(Throwable throwable, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(ERROR(throwable, status), headers, status);
    }


}