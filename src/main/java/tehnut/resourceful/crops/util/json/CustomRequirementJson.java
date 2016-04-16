package tehnut.resourceful.crops.util.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.world.EnumDifficulty;
import tehnut.resourceful.crops.api.base.Requirement;
import tehnut.resourceful.crops.api.base.RequirementBuilder;
import tehnut.resourceful.crops.api.util.BlockStack;
import tehnut.resourceful.crops.util.helper.JsonHelper;

import java.lang.reflect.Type;
import java.util.Locale;

public class CustomRequirementJson implements JsonSerializer<Requirement>, JsonDeserializer<Requirement> {

    @Override
    public Requirement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonHelper jsonHelper = new JsonHelper(json);
        BlockStack growthReq = null;
        if (json.getAsJsonObject().get("growthReq") != null)
            growthReq = context.deserialize(json.getAsJsonObject().get("growthReq"), new TypeToken<BlockStack>() {
            }.getType());
        String difficulty = jsonHelper.getNullableString("minDifficulty", "PEACEFUL").toUpperCase(Locale.ENGLISH);
        int lightLevelMin = jsonHelper.getNullableInteger("lightLevelMin", 9);
        int lightLevelMax = jsonHelper.getNullableInteger("lightLevelMax", 15);

        RequirementBuilder builder = new RequirementBuilder();
        builder.setGrowthReq(growthReq);
        builder.setDifficulty(EnumDifficulty.valueOf(difficulty));
        builder.setLightLevelMin(lightLevelMin);
        builder.setLightLevelMax(lightLevelMax);

        return builder.build();
    }

    @Override
    public JsonElement serialize(Requirement src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.add("growthReq", context.serialize(src.getGrowthReq()));
        jsonObject.addProperty("minDifficulty", src.getDifficulty().toString());
        jsonObject.addProperty("lightLevelMin", src.getLightLevelMin());
        jsonObject.addProperty("lightLevelMax", src.getLightLevelMax());

        return jsonObject;
    }
}
