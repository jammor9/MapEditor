package org.jammor9.mappointeditor.Utils;

import com.google.gson.*;
import org.jammor9.mappointeditor.models.ModelComposite;

import java.lang.reflect.Type;

public class AbstractSerializer<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static final String CLASS_META_KEY = "CLASS_META_KEY";

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject o = jsonElement.getAsJsonObject();
        String className = o.get(CLASS_META_KEY).getAsString();
        try {
            Class<?> cls = Class.forName(className);
            return jsonDeserializationContext.deserialize(jsonElement, cls);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonElement serialize(T resource, Type type, JsonSerializationContext jsonSerializationContext) {
        Gson gson = new Gson();
        gson.toJson(resource, resource.getClass());
        JsonElement jsonElement = gson.toJsonTree(resource);
        return jsonElement;
    }
}
