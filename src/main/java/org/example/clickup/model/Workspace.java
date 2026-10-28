package org.example.clickup.model;

import java.util.List;

public record Workspace(String id, String name, String color, String avatar, List<WorkspaceMember> members) {}
