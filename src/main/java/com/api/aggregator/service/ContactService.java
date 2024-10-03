package com.api.aggregator.service;

import com.api.aggregator.client.KenectLabsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KenectLabsService {

    private final KenectLabsClient kenectLabsClient;

}