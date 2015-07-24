package tehnut.resourceful.crops.util.serialization.serializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.world.EnumDifficulty;
import tehnut.resourceful.crops.api.base.SeedReq;
import tehnut.resourceful.crops.api.base.SeedReqBuilder;
import tehnut.resourceful.crops.api.util.BlockStack;
import tehnut.resourceful.crops.util.helper.JsonHelper;

import java.lang.reflect.Type;

public class CustomSeedReqJson implements JsonDeserializer<SeedReq>, JsonSerializer<SeedReq> {

    @Override
    public SeedReq deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonHelper helper = new JsonHelper(json);

        BlockStack blockStack = context.deserialize(json.getAsJsonObject().get("blockStack"), new TypeToken<BlockStack>() { }.getType());
        String difficulty = helper.getNullableString("difficulty", "PEACEFUL");
        int lightLevelMin = helper.getNullableInteger("lightLevelMin", 9);
        int lightLevelMax = helper.getNullableInteger("lightLevelMax", Integer.MAX_VALUE);

        SeedReqBuilder builder = new SeedReqBuilder();
        builder.setGrowthReq(blockStack);
        builder.setDifficulty(EnumDifficulty.valueOf(difficulty));
        builder.setLightLevelMin(lightLevelMin);
        builder.setLightLevelMax(lightLevelMax);

        return builder.build();
    }

    @Override
    public JsonElement serialize(SeedReq src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        if (src.getGrowthReq() != null)
            jsonObject.add("blockStack", context.serialize(src.getGrowthReq()));
        if (src.getDifficulty() != EnumDifficulty.PEACEFUL)
            jsonObject.addProperty("difficulty", src.getDifficulty().toString());
        if (src.getLightLevelMin() != 9)
            jsonObject.addProperty("lightLevelMin", src.getLightLevelMin());
        if (src.getLightLevelMax() != Integer.MAX_VALUE)
            jsonObject.addProperty("lightLevelMax", src.getLightLevelMax());

        return jsonObject;
    }
}
