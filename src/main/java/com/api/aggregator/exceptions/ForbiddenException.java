package com.api.aggregator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ForbiddenException extends AggregatorException{
    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        pb.setTitle("Forbidden");
        pb.setDetail("You don't have rights to access this resource.");
        return pb;
    }

}
