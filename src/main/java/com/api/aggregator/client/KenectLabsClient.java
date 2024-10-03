package com.api.aggregator.client;

import com.api.aggregator.client.dto.ContactDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "kenectLabsClient",
        url = "${client.authorization-service.url}"
)
public interface KenectLabsClient {

    @GetMapping("/contacts")
    ResponseEntity<List<ContactDTO>> getContacts(@RequestParam("page") int page);

    @GetMapping("/contacts/{id}")
    ContactDTO getContactById(@PathVariable("id") String id);
}