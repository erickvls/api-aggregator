package com.api.aggregator.controller.advice;

import com.api.aggregator.exceptions.AggregatorException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(AggregatorException.class)
    public ProblemDetail handlePicPayException(AggregatorException e) {
        return e.toProblemDetail();
    }

}

