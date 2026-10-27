package org.example.clickup.api;

import java.util.List;
import org.example.clickup.model.Goal;
import org.example.clickup.model.GoalFolder;

public record GoalsResponse(List<Goal> goals, List<GoalFolder> folders) {}
