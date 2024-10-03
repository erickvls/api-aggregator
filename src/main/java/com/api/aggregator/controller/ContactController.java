package com.api.aggregator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContactController {

    @GetMapping("/contacts")
    public ResponseEntity<String> getContacts() {
        return ResponseEntity.ok("OK");
    }


    @GetMapping("/contacts/{id}")
    public ResponseEntity<String> getContactById(@PathVariable("id") String id) {
        return ResponseEntity.ok("OK");
    }
}

