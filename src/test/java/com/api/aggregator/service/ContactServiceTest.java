package com.api.aggregator.service;

import com.api.aggregator.client.KenectLabsClient;
import com.api.aggregator.client.dto.Contact;
import com.api.aggregator.client.dto.ContactsResponse;
import com.api.aggregator.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private KenectLabsClient kenectLabsClient;

    @InjectMocks
    private ContactService contactService;

    @Test
    void shouldReturnAllContactsSuccessfully() {
        // Given
        var contactsPage1 = new ContactsResponse(List.of(new Contact()));
        var contactsPage2 = new ContactsResponse(List.of(new Contact()));
        var headers = new HttpHeaders();
        headers.add("Total-Pages", "2");

        when(kenectLabsClient.getContacts(1, "valid_token"))
                .thenReturn(new ResponseEntity<>(contactsPage1, headers, HttpStatus.OK));
        when(kenectLabsClient.getContacts(2, "valid_token"))
                .thenReturn(new ResponseEntity<>(contactsPage2, headers, HttpStatus.OK));

        // When
        var allContacts = contactService.getAllContacts("valid_token");

        // Then
        assertEquals(2, allContacts.size());
        verify(kenectLabsClient, times(2)).getContacts(anyInt(), eq("valid_token"));
    }

    @Test
    void shouldThrowAggregatorExceptionWhenServerErrorOccurs() {
        // Given
        when(kenectLabsClient.getContacts(1,"valid_token"))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));

        // When & Then
        assertEquals(0, contactService.getAllContacts("valid_token").size());
    }

    @Test
    void shouldThrowNumberFormatExceptionWhenTotalPagesIsInvalid() {
        // Given
        var contactsResponse = new ContactsResponse(List.of(new Contact()));
        var headers = new HttpHeaders();
        headers.add("Total-Pages", "invalid_number");

        when(kenectLabsClient.getContacts(1, "valid_token"))
                .thenReturn(new ResponseEntity<>(contactsResponse, headers, HttpStatus.OK));

        // When & Then
        assertThrows(NumberFormatException.class, () -> contactService.getAllContacts("valid_token"));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenContactBodyIsNull() {
        // Given
        when(kenectLabsClient.getContactById("valid_id", "valid_token"))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        // When & Then
        assertThrows(NotFoundException.class, () -> contactService.getContactById("valid_id", "valid_token"));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenContactNotFound() {
        // Given
        when(kenectLabsClient.getContactById("invalid_id", "valid_token"))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        // When & Then
        assertThrows(NotFoundException.class, () -> contactService.getContactById("invalid_id", "valid_token"));
    }

    @Test
    void shouldReturnEmptyListWhenNoContactsFound() {
        // Given
        var contactsResponse = new ContactsResponse(Collections.emptyList());
        var headers = new HttpHeaders();
        headers.add("Total-Pages", "1");

        when(kenectLabsClient.getContacts(1, "valid_token"))
                .thenReturn(new ResponseEntity<>(contactsResponse, headers, HttpStatus.OK));

        // When
        var allContacts = contactService.getAllContacts("valid_token");

        // Then
        assertTrue(allContacts.isEmpty());
        verify(kenectLabsClient, times(1)).getContacts(1, "valid_token");
    }


    @Test
    void shouldHandleMissingTotalPagesHeader() {
        // Given
        var contactsResponse = new ContactsResponse(List.of(new Contact()));
        var headers = new HttpHeaders(); // No "Total-Pages" header

        when(kenectLabsClient.getContacts(1, "valid_token"))
                .thenReturn(new ResponseEntity<>(contactsResponse, headers, HttpStatus.OK));

        // When
        var allContacts = contactService.getAllContacts("valid_token");

        // Then
        assertEquals(1, allContacts.size());
    }

}
