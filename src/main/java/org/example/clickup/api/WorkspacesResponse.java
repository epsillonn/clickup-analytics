package org.example.clickup.api;

import java.util.List;
import org.example.clickup.model.Workspace;

public record WorkspacesResponse(List<Workspace> teams) {}
