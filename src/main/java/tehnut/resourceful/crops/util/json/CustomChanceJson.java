package tehnut.resourceful.crops.util.json;

import com.google.gson.*;

import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.api.base.Chance;
import tehnut.resourceful.crops.api.base.ChanceBuilder;
import tehnut.resourceful.crops.util.helper.JsonHelper;

import java.lang.reflect.Type;

public class CustomChanceJson implements JsonSerializer<Chance>, JsonDeserializer<Chance> {

    @Override
    public Chance deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonHelper jsonHelper = new JsonHelper(json);
        double extraSeed = jsonHelper.getNullableDouble("extraSeed", ConfigHandler.defaultExtraSeedChance);
        double essenceDrop = jsonHelper.getNullableDouble("essenceDrop", ConfigHandler.defaultEssenceDropChance);

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
