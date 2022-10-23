package com.github.hetikk.bootstrap.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class PatchHelper {

    private final ObjectMapper objectMapper;

    public <T> T applyPatch(JsonNode updates, T currentState, Class<T> type) {
        try {
            if (updates == null || updates.isNull()) {
                updates = objectMapper.createObjectNode();
            }
            JsonMergePatch patch = JsonMergePatch.fromJson(updates);
            JsonNode mergedSite = patch.apply(objectMapper.valueToTree(currentState));
            return objectMapper.readerFor(type).readValue(mergedSite);
        } catch (IOException | JsonPatchException e) {
            throw new RuntimeException(e);
        }
    }

}
