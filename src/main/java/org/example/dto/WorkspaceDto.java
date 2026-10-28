package org.example.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
public  class WorkspaceDto {
    private String id;
    private String name;
    private List<WorkspaceMemberDto> members;

}

