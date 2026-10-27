package org.example.clickup.api;

import java.util.List;
import org.example.clickup.model.User;

public record MembersResponse(List<User> members) {}
