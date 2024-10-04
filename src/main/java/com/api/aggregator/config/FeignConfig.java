package com.api.aggregator.config;

import com.api.aggregator.client.decoder.CustomFeignErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 Configuration class that registers the custom error decoder for Feign clients.
 */
@Configuration
public class FeignConfig {

    /*
    Provides the custom error decoder bean.
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}