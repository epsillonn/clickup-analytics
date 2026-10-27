package org.example.clickup;

import java.io.InputStream;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

public final class ClickUpFixtures {
    private ClickUpFixtures() {}

    public static <T> T read(JsonMapper mapper, String path, Class<T> type) {
        return mapper.readValue(open(path), type);
    }

    public static <T> T read(JsonMapper mapper, String path, TypeReference<T> type) {
        return mapper.readValue(open(path), type);
    }

    private static InputStream open(String path) {
        InputStream body = ClickUpFixtures.class.getResourceAsStream("/clickup/" + path);
        if (body == null) {
            throw new IllegalArgumentException("Missing fixture " + path);
        }
        return body;
    }
}
