package tehnut.resourceful.crops.util.json;

import com.google.gson.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import tehnut.resourceful.crops.util.helper.JsonHelper;

import java.lang.reflect.Type;

public class CustomItemStackJson implements JsonDeserializer<ItemStack>, JsonSerializer<ItemStack> {

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonHelper helper = new JsonHelper(json);

        String name = helper.getString("name");
        int amount = helper.getNullableInteger("amount", 1);
        int meta = helper.getNullableInteger("meta", 0);

        return new ItemStack(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(name)) ? ForgeRegistries.ITEMS.getValue(new ResourceLocation(name)) : null, amount, meta);
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", src.getItem().getRegistryName().toString());
        jsonObject.addProperty("amount", src.stackSize);
        jsonObject.addProperty("meta", src.getItemDamage());

        return jsonObject;
    }

}
