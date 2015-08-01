package tehnut.resourceful.crops.util.serialization.serializers;

import com.google.gson.*;
import net.minecraftforge.fml.common.registry.GameData;
import tehnut.resourceful.crops.util.BlockStack;
import tehnut.resourceful.crops.util.helper.JsonHelper;

import java.lang.reflect.Type;

public class CustomBlockStackJson implements JsonDeserializer<BlockStack>, JsonSerializer<BlockStack> {

    @Override
    public BlockStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonHelper helper = new JsonHelper(json);

        String name = helper.getString("name");
        int meta = helper.getNullableInteger("meta", 0);

        return new BlockStack(GameData.getBlockRegistry().containsKey(name) ? GameData.getBlockRegistry().getObject(name).getStateFromMeta(meta) : null);
    }

    @Override
    public JsonElement serialize(BlockStack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", GameData.getBlockRegistry().getNameForObject(src.getBlock()).toString());
        jsonObject.addProperty("meta", src.getMeta());

        return jsonObject;
    }
}
