package org.example.clickup.webhook;

import org.example.clickup.model.HierarchyRef;

/** The List a task left or entered in a {@code section_moved} history item; {@code category} is its Folder and {@code project} its Space. */
public record MovedLocation(String id, String name, HierarchyRef category, HierarchyRef project) {}
