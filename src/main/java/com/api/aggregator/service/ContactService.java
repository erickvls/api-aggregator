package com.api.aggregator.service;

import com.api.aggregator.client.KenectLabsClient;
import com.api.aggregator.client.dto.Contact;
import com.api.aggregator.client.dto.ContactResponse;
import com.api.aggregator.client.dto.ContactsResponse;
import com.api.aggregator.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final KenectLabsClient kenectLabsClient;
    private static final String TOTAL_PAGES_HEADER = "Total-Pages";

    public List<Contact> getAllContacts(String token) {
        var allContacts = new ArrayList<Contact>();
        var page = 1;
        int totalPages;

        do {
            var response = fetchContactsFromPage(page, token);
            var responseBody = response.getBody();

            if (responseBody != null && responseBody.getContacts() != null) {
                allContacts.addAll(responseBody.getContacts());
            }

            totalPages = getTotalPages(response.getHeaders());
            page++;
        } while (page <= totalPages);

        return allContacts;
    }

    private ResponseEntity<ContactsResponse> fetchContactsFromPage(int page, String token) {
        return kenectLabsClient.getContacts(page, token);
    }

    private int getTotalPages(HttpHeaders headers) {
        return headers.getFirst(TOTAL_PAGES_HEADER) instanceof String value ? Integer.parseInt(value) : 0;
    }

    public Contact getContactById(String id, String token) {
        var contact = kenectLabsClient.getContactById(id, token);
        return Optional.ofNullable(contact.getBody())
                .map(ContactResponse::getContact)
                .orElseThrow(NotFoundException::new);
    }
}