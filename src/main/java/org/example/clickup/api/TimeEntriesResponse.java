package org.example.clickup.api;

import java.util.List;
import org.example.clickup.model.TimeEntry;

public record TimeEntriesResponse(List<TimeEntry> data) {}
