package com.api.aggregator.client.decoder;

import com.api.aggregator.exceptions.AggregatorException;
import com.api.aggregator.exceptions.ForbiddenException;
import com.api.aggregator.exceptions.NotFoundException;
import com.api.aggregator.exceptions.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomFeignErrorDecoder implements ErrorDecoder {

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