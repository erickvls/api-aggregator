package com.api.aggregator.enums;

public enum SourceType {
    KENECT_LABS("KENECT_LABS");

    private final String value;

    SourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
