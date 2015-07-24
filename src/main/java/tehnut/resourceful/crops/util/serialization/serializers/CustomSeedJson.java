package tehnut.resourceful.crops.util.serialization.serializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.base.SeedBuilder;
import tehnut.resourceful.crops.api.base.SeedReq;
import tehnut.resourceful.crops.api.base.SeedReqBuilder;
import tehnut.resourceful.crops.util.Utils;
import tehnut.resourceful.crops.util.helper.JsonHelper;

import java.awt.*;
import java.lang.reflect.Type;

public class CustomSeedJson implements JsonDeserializer<Seed>, JsonSerializer<Seed> {

    @Override
    public Seed deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonHelper helper = new JsonHelper(json);

        String name = helper.getString("name");
        int tier = helper.getNullableInteger("tier", 1);
        int amount = helper.getNullableInteger("amount", 1);
        String input = helper.getString("input");
        String output = helper.getNullableString("output", null);
        String color = helper.getString("color");
        SeedReq seedReq = new SeedReqBuilder().build();
        if (json.getAsJsonObject().get("seedReq") != null)
            seedReq = context.deserialize(json.getAsJsonObject().get("seedReq"), new TypeToken<SeedReq>() { }.getType());

        SeedBuilder builder = new SeedBuilder();
        builder.setName(name);
        builder.setTier(tier);
        builder.setAmount(amount);
        builder.setInput(input);
        builder.setOutput(Utils.parseItemStack(output, false));
        builder.setColor(Color.decode(color));
        builder.setSeedReq(seedReq);

        return builder.build();
    }

    @Override
    public JsonElement serialize(Seed src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("tier", src.getTier());
        jsonObject.addProperty("amount", src.getAmount());
        jsonObject.addProperty("input", src.getInput());
        jsonObject.addProperty("output", Utils.itemStackToString(src.getOutput()));
        jsonObject.addProperty("color", "#" + Integer.toHexString(src.getColor().getRGB()).substring(2).toUpperCase());
        if (!isSeedReqDefault(src.getSeedReq()))
            jsonObject.add("seedReq", context.serialize(src.getSeedReq()));

        return jsonObject;
    }

    /**
     * Used to determine whether to add the seedReq
     * field to the JSON printing.
     *
     * @param seedReq - SeedReq to check if default
     * @return        - Whether the given SeedReq is default
     */
    private static boolean isSeedReqDefault(SeedReq seedReq) {
        return seedReq.getGrowthReq() == null && seedReq.getLightLevelMin() == 9 && seedReq.getLightLevelMax() == Short.MAX_VALUE;
    }
}
