package com.api.aggregator.service;

import com.api.aggregator.client.KenectLabsClient;
import com.api.aggregator.client.dto.Contact;
import com.api.aggregator.client.dto.ContactResponse;
import com.api.aggregator.client.dto.ContactsResponse;
import com.api.aggregator.enums.SourceType;
import com.api.aggregator.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final KenectLabsClient kenectLabsClient;

    private static final String TOTAL_PAGES_HEADER = "Total-Pages";

    /*
    Retrieves all contacts by fetching data across multiple pages.
     */
    public List<Contact> getAllContacts(String token) {
        var initialResponse = fetchContactsFromPage(1, token);

        if (initialResponse.getBody() == null) {
            return new ArrayList<>();
        }

        var totalPages = getTotalPages(initialResponse.getHeaders());
        var tasks = new ArrayList<CompletableFuture<List<Contact>>>();

        for (int page = 1; page <= totalPages; page++) {
            var task = fetchContactsAsync(page, token);
            tasks.add(task);
        }

        var allContacts = new ArrayList<Contact>();
        for (var task : tasks) {
            var result = task.join();
            allContacts.addAll(result);
        }
        return allContacts;
    }


    public Contact getContactById(String id, String token) {
        var contact = kenectLabsClient.getContactById(id, token);
        return Optional.ofNullable(contact.getBody())
                .map(ContactResponse::getContact)
                .orElseThrow(NotFoundException::new);
    }


    /*
     * Sets the source type for each contact in the given list.
     */
    public void setSource(List<Contact> contacts, SourceType sourceType){
        contacts.forEach(contact -> contact.setSource(sourceType));
    }

    /*
    Helper method to fetch contacts from a specific page using the Feign client.
     */
    private ResponseEntity<ContactsResponse> fetchContactsFromPage(int page, String token) {
        return kenectLabsClient.getContacts(page, token);
    }

    /*
    Helper method to extract the total number of pages from the HTTP headers of a response.
     */
    private int getTotalPages(HttpHeaders headers) {
        return headers.getFirst(TOTAL_PAGES_HEADER) instanceof String value ? Integer.parseInt(value) : 0;
    }

    /*
     * Asynchronously fetches contacts from a specific page using the Feign client.
     * The fetched contacts are then set with the source type {@link SourceType#KENECT_LABS}.
     */

    public CompletableFuture<List<Contact>> fetchContactsAsync(int page, String token) {
        return CompletableFuture.supplyAsync(() -> {
            var response = fetchContactsFromPage(page, token);
            if (response.getBody() != null) {
                var contacts = response.getBody().getContacts();
                setSource(contacts, SourceType.KENECT_LABS);
                return contacts;
            }
            return new ArrayList<>();
        });
    }

}