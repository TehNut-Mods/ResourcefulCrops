package tehnut.resourceful.crops2.core.json;

import com.google.gson.*;

import java.lang.reflect.Type;

public abstract class SerializerBase<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return context.deserialize(json, typeOfT);
    }

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    public abstract Type getType();
}
