package tehnut.resourceful.crops.util.serialization.serializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.block.Block;
import tehnut.resourceful.crops.api.base.Compat;
import tehnut.resourceful.crops.api.base.CompatBuilder;
import tehnut.resourceful.crops.api.util.BlockStack;
import tehnut.resourceful.crops.util.helper.JsonHelper;

import java.lang.reflect.Type;

public class CustomCompatJson implements JsonDeserializer<Compat>, JsonSerializer<Compat> {

    @Override
    public Compat deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Compat.CompatExNihilio compatExNihilio = null;
        if (json.getAsJsonObject().get("exnihilio") != null)
            compatExNihilio = context.deserialize(json.getAsJsonObject().get("exnihilio"), new TypeToken<Compat.CompatExNihilio>() { }.getType());

        CompatBuilder builder = new CompatBuilder();
        builder.setCompatExNihilio(compatExNihilio);

        return builder.build();
    }

    @Override
    public JsonElement serialize(Compat src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        if (src.getCompatExNihilio() != null)
            jsonObject.add("exnihilio", context.serialize(src.getCompatExNihilio()));

        return jsonObject;
    }

    public static class CustomCompatExNihilioJson implements JsonDeserializer<Compat.CompatExNihilio>, JsonSerializer<Compat.CompatExNihilio> {

        @Override
        public Compat.CompatExNihilio deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonHelper helper = new JsonHelper(json);

            String sourceBlock = cleanString(json.getAsJsonObject().get("sourceBlock").toString());
            int sieveChance = helper.getNullableInteger("sieveChance", 0);

            String[] blockInfo = sourceBlock.split(":");

            CompatBuilder.CompatExNihilioBuilder builder = new CompatBuilder.CompatExNihilioBuilder();
            builder.setSourceBlock(new BlockStack(Block.getBlockFromName(blockInfo[0] + ":" + blockInfo[1]), Integer.parseInt(blockInfo[2])));
            builder.setSieveChance(sieveChance);

            return builder.build();
        }

        @Override
        public JsonElement serialize(Compat.CompatExNihilio src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("sourceBlock", context.serialize(src.getSourceBlock()));
            jsonObject.add("sieveChance", context.serialize(src.getSieveChance()));

            return jsonObject;
        }

        // Hacky way to get around an issue I'm having, but it works.
        private static String cleanString(String input) {
            return input.replace("{", "").replace("}", "").replace("\"name\":\"", "").replace("\",\"meta\"", "");
        }
    }
}