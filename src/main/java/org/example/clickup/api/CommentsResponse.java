package org.example.clickup.api;

import java.util.List;
import org.example.clickup.model.Comment;

public record CommentsResponse(List<Comment> comments) {}
