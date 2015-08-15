package tehnut.resourceful.crops.util.serialization.serializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import tehnut.resourceful.crops.api.base.*;
import tehnut.resourceful.crops.util.Utils;
import tehnut.resourceful.crops.util.helper.JsonHelper;

import java.awt.*;
import java.lang.reflect.Type;

public class CustomSeedJson implements JsonDeserializer<Seed>, JsonSerializer<Seed> {

    static SeedReq defaultReq = new SeedReqBuilder().build();
    static Chance defaultChance = new ChanceBuilder().build();

    @Override
    public Seed deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonHelper helper = new JsonHelper(json);

        String name = helper.getString("name");
        int tier = helper.getNullableInteger("tier", 1);
        int amount = helper.getNullableInteger("amount", 1);
        boolean farmland = helper.getNullableBoolean("nether", false);
        String input = helper.getString("input");
        String output = helper.getNullableString("output", null);
        String secondOutput = helper.getNullableString("secondOutput", null);
        String thirdOutput = helper.getNullableString("thirdOutput", null);
        String color = helper.getString("color");
        SeedReq seedReq = new SeedReqBuilder().build();
        Chance chance = new ChanceBuilder().build();
        Compat compat = new CompatBuilder().build();
        if (json.getAsJsonObject().get("seedReq") != null)
            seedReq = context.deserialize(json.getAsJsonObject().get("seedReq"), new TypeToken<SeedReq>() { }.getType());
        if (json.getAsJsonObject().get("chance") != null)
            chance = context.deserialize(json.getAsJsonObject().get("chance"), new TypeToken<Chance>() { }.getType());
        if (json.getAsJsonObject().get("compat") != null)
            compat = context.deserialize(json.getAsJsonObject().get("compat"), new TypeToken<Compat>() { }.getType());

        SeedBuilder builder = new SeedBuilder();
        builder.setName(name);
        builder.setTier(tier);
        builder.setAmount(amount);
        builder.setNether(farmland);
        builder.setInput(input);
        builder.setOutput(Utils.parseItemStack(output, false));
        builder.setSecondOutput(Utils.parseItemStack(secondOutput, false));
        builder.setThirdOutput(Utils.parseItemStack(thirdOutput, false));
        builder.setColor(Color.decode(color));
        builder.setSeedReq(seedReq);
        builder.setChance(chance);
        builder.setCompat(compat);

        return builder.build();
    }

    @Override
    public JsonElement serialize(Seed src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("tier", src.getTier());
        jsonObject.addProperty("amount", src.getAmount());
        if (!src.getNether())
            jsonObject.addProperty("nether", src.getNether());
        jsonObject.addProperty("input", src.getInput());
        jsonObject.addProperty("output", Utils.itemStackToString(src.getOutput()));
        if (src.getSecondOutput() != null)
            jsonObject.addProperty("secondOutput", Utils.itemStackToString(src.getSecondOutput()));
        if (src.getThirdOutput() != null)
            jsonObject.addProperty("thirdOutput", Utils.itemStackToString(src.getThirdOutput()));
        jsonObject.addProperty("color", "#" + Integer.toHexString(src.getColor().getRGB()).substring(2).toUpperCase());
        if (!isSeedReqDefault(src.getSeedReq()))
            jsonObject.add("seedReq", context.serialize(src.getSeedReq()));
        if (!isChanceDefault(src.getChance()))
            jsonObject.add("chance", context.serialize(src.getChance()));
        if (src.getCompat() != null)
            jsonObject.add("compat", context.serialize(src.getCompat()));

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
        return seedReq.equals(defaultReq);
    }

    /**
     * Used to determine whether to add the seedReq
     * field to the JSON printing.
     *
     * @param chance - {@link Chance} to check if default
     * @return       - Whether the given Chance is default
     */
    private static boolean isChanceDefault(Chance chance) {
        return chance.equals(defaultChance);
    }
}
