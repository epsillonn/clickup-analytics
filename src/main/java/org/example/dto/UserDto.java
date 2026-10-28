package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String initials;

    @JsonProperty("profilePicture")
    private String profilePicture;

    @JsonProperty("role_key")
    private String roleKey;

    @JsonProperty("date_invited")
    private String dateInvited;

    @JsonProperty("date_joined")
    private String dateJoined;




}
