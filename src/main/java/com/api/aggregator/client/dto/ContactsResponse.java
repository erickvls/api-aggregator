package com.api.aggregator.client.dto;

import lombok.Data;

import java.util.List;

@Data
public class ContactsResponse {
    private List<Contact> contacts;
}
