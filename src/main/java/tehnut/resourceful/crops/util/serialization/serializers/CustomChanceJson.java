package tehnut.resourceful.crops.util.serialization.serializers;

import com.google.gson.*;
import tehnut.resourceful.crops.base.Chance;
import tehnut.resourceful.crops.base.ChanceBuilder;
import tehnut.resourceful.crops.util.helper.JsonHelper;

import java.lang.reflect.Type;

public class CustomChanceJson implements JsonDeserializer<Chance>, JsonSerializer<Chance> {

    @Override
    public Chance deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonHelper helper = new JsonHelper(json);

        double extraSeed = helper.getNullableDouble("extraSeed", 0.0);
        double essenceDrop = helper.getNullableDouble("essenceDrop", 0.0);

        ChanceBuilder builder = new ChanceBuilder();
        builder.setExtraSeed(extraSeed);
        builder.setEssenceDrop(essenceDrop);

        return builder.build();
    }

    @Override
    public JsonElement serialize(Chance src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("extraSeed", src.getExtraSeed());
        jsonObject.addProperty("essenceDrop", src.getEssenceDrop());

        return jsonObject;
    }
}
