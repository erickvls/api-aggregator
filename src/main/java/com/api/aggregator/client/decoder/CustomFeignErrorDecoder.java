package com.api.aggregator.client.decoder;

import com.api.aggregator.exceptions.AggregatorException;
import com.api.aggregator.exceptions.ForbiddenException;
import com.api.aggregator.exceptions.NotFoundException;
import com.api.aggregator.exceptions.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

/**
 *  Handles the translation of HTTP response status codes into specific exceptions for Feign client requests.
 */
public class CustomFeignErrorDecoder implements ErrorDecoder {

    /*
    Decodes the HTTP response and returns appropriate exception based on status code (e.g., 403, 404, 401).
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        var status = HttpStatus.valueOf(response.status());

        return switch (status) {
            case FORBIDDEN -> new ForbiddenException();
            case UNAUTHORIZED -> new UnauthorizedException();
            case NOT_FOUND -> new NotFoundException();
            default -> new AggregatorException();
        };
    }
}