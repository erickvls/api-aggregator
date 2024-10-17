package com.api.aggregator.client.dto;

import com.api.aggregator.enums.SourceType;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * The fields createdAt and updatedAt are annotated with {@link JsonAlias}
 * to map the JSON property "updated_at and "createdAt" but they will keep
 * camelCase for serialization".
 */
@Data
public class Contact {
    private String id;
    private String name;
    private String email;
    @JsonAlias("created_at")
    private String createdAt;
    @JsonAlias("updated_at")
    private String updatedAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private SourceType source;
}
