package org.example.clickup.api;

import java.util.List;
import org.example.clickup.model.ClickUpList;

public record ListsResponse(List<ClickUpList> lists) {}
