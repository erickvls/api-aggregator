package com.api.aggregator.controller;

import com.api.aggregator.client.dto.Contact;
import com.api.aggregator.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getContacts(
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(contactService.getAllContacts(token));
    }


    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactById(
            @PathVariable("id") String id,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(contactService.getContactById(id,token));
    }
}

