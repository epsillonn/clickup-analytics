package org.example.clickup.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record User(
        Long id,
        String username,
        String email,
        String color,
        String initials,
        @JsonProperty("profilePicture") String profilePicture) {}
