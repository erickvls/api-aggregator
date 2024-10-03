package com.api.aggregator.service;

import com.api.aggregator.client.KenectLabsClient;
import com.api.aggregator.client.dto.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final KenectLabsClient kenectLabsClient;

    public Contact getContactById(String id, String token) {
        return kenectLabsClient.getContactById(id, token).getContact();
    }
}