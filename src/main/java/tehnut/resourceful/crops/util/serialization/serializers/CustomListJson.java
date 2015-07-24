package tehnut.resourceful.crops.util.serialization.serializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.registry.SeedRegistry;

import java.lang.reflect.Type;
import java.util.List;

public class CustomListJson implements JsonDeserializer<List<Seed>>, JsonSerializer<List<Seed>> {

    @Override
    public List<Seed> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Seed> list = context.deserialize(json.getAsJsonObject().get("seeds"), new TypeToken<List<Seed>>() {
        }.getType());

        for (Seed seed : list)
            SeedRegistry.registerSeed(seed);

        return list;
    }

    @Override
    public JsonElement serialize(List<Seed> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("seeds", context.serialize(SeedRegistry.getSeedList()));

        return jsonObject;
    }
}
