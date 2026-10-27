package org.example.clickup.api;

import java.util.List;
import org.example.clickup.model.Folder;

public record FoldersResponse(List<Folder> folders) {}
