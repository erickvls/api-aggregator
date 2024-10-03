package com.api.aggregator.client;

import com.api.aggregator.client.dto.Contact;
import com.api.aggregator.client.dto.ContactResponse;
import com.api.aggregator.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "kenectLabsClient",
        url = "${client.authorization-service.url}",
        configuration = FeignConfig.class
)
public interface KenectLabsClient {

    @GetMapping("/contacts")
    ResponseEntity<List<Contact>> getContacts(@RequestParam("page") int page, @RequestHeader("Authorization") String token);

    @GetMapping("/contacts/{id}")
    ContactResponse getContactById(@PathVariable("id") String id, @RequestHeader("Authorization") String token);
}