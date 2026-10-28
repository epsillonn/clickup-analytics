package org.example.clickup.api;

import java.util.List;
import org.example.clickup.model.CustomField;

public record CustomFieldsResponse(List<CustomField> fields) {}
