package com.api.aggregator.client;

import com.api.aggregator.client.dto.ContactResponse;
import com.api.aggregator.client.dto.ContactsResponse;
import com.api.aggregator.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "kenectLabsClient",
        url = "${client.authorization-service.url}",
        configuration = FeignConfig.class
)
public interface KenectLabsClient {

    @GetMapping("/contacts")
    ResponseEntity<ContactsResponse> getContacts(@RequestParam("page") int page, @RequestHeader("Authorization") String token);

    @GetMapping("/contacts/{id}")
    ResponseEntity<ContactResponse> getContactById(@PathVariable("id") String id, @RequestHeader("Authorization") String token);
}