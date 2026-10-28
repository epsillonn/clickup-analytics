package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.example.dto.UserDto;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class WorkspaceMemberDto {
    private UserDto user;


    @JsonProperty("role_key")
    private String roleKey;

    @JsonProperty("date_joined")
    private String dateJoined;

    @JsonProperty("date_invited")
    private String dateInvited;

    @JsonProperty("invited_by")
    private UserDto invitedBy;




}
