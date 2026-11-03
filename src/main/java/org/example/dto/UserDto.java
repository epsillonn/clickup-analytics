package org.example.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDto(
        Long id,
        String username,
        String email,
        @JsonProperty("profilePicture") String profilePicture,
        @JsonProperty("role_key") String roleKey,
        @JsonProperty("date_invited") String dateInvited,
        @JsonProperty("date_joined") String dateJoined

) {
}
