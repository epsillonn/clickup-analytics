package org.example.clickup.api;

import java.util.List;
import org.example.clickup.model.Tag;

public record TagsResponse(List<Tag> tags) {}
