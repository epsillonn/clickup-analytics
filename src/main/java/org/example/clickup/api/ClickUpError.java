package org.example.clickup.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ClickUpError(
        @JsonAlias("error") String err,
        @JsonProperty("ECODE") String ecode) {}
