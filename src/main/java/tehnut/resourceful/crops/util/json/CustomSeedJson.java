package tehnut.resourceful.crops.util.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.item.ItemStack;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.api.base.*;
import tehnut.resourceful.crops.util.helper.JsonHelper;

import java.awt.Color;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CustomSeedJson implements JsonDeserializer<Seed>, JsonSerializer<Seed> {

    private static final Requirement DEFAULT_REQUIREMENT = new RequirementBuilder().build();
    private static final Chance DEFAULT_CHANCE = new ChanceBuilder().build();

    @Override
    public Seed deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonHelper jsonHelper = new JsonHelper(json);
        String name = jsonHelper.getString("name");
        int tier = jsonHelper.getNullableInteger("tier", 1);
        int amount = jsonHelper.getNullableInteger("amount", 1);
        String input = jsonHelper.getString("input");
        
        List<Output> outputs = new ArrayList<Output>();
        
        //Old style
        if (json.getAsJsonObject().get("output").isJsonObject()) {
        	OutputBuilder builder = new OutputBuilder();
        	builder.setOutputStack((ItemStack) context.deserialize(json.getAsJsonObject().get("output"), new TypeToken<ItemStack>() {
        	}.getType()));
        	builder.setRecipe("default");
			ResourcefulAPI.logger.info("Old style output found: " + json.getAsJsonObject().get("output"));
        	outputs.add(builder.build());
        }
        //New style
        else if (json.getAsJsonObject().get("output").isJsonArray()) {
        	for (JsonElement o : json.getAsJsonObject().get("output").getAsJsonArray()) {
        		if (!o.isJsonObject()) {
        			ResourcefulAPI.logger.warn("Ignoring broken output: " + o);
        			continue;
        		}
        		Output output = context.deserialize(o.getAsJsonObject(), new TypeToken<Output>() {
                }.getType());
            	outputs.add(output);
        	}
        }
        
        //Compat
        if (json.getAsJsonObject().get("secondOutput") != null) {
        	OutputBuilder builder = new OutputBuilder();
        	builder.setOutputStack((ItemStack) context.deserialize(json.getAsJsonObject().get("secondOutput"), new TypeToken<ItemStack>() {
        	}.getType()));
        	builder.setRecipe("cross");
			ResourcefulAPI.logger.info("Old style second output found: " + json.getAsJsonObject().get("secondOutput"));
        	outputs.add(builder.build());
        }
        if (json.getAsJsonObject().get("thirdOutput") != null) {
        	OutputBuilder builder = new OutputBuilder();
        	builder.setOutputStack((ItemStack) context.deserialize(json.getAsJsonObject().get("thirdOutput"), new TypeToken<ItemStack>() {
        	}.getType()));
        	builder.setRecipe("2x2");
			ResourcefulAPI.logger.info("Old style third output found: " + json.getAsJsonObject().get("thirdOutput"));
        	outputs.add(builder.build());
        }
       
        
        String color = jsonHelper.getString("color");
        Chance chance = new ChanceBuilder().build();
        Requirement requirement = new RequirementBuilder().build();
        if (json.getAsJsonObject().get("requirement") != null)
            requirement = context.deserialize(json.getAsJsonObject().get("requirement"), new TypeToken<Requirement>() {
            }.getType());
        if (json.getAsJsonObject().get("chance") != null)
            chance = context.deserialize(json.getAsJsonObject().get("chance"), new TypeToken<Chance>() {
            }.getType());

        SeedBuilder builder = new SeedBuilder();
        builder.setName(name);
        builder.setTier(tier);
        builder.setAmount(amount);
        builder.setInput(input);
        builder.setOutput(outputs);
        try {
            builder.setColor(Color.decode(color));
        } catch (NumberFormatException e) {
            // Pokeball!
        }
        builder.setChance(chance);
        builder.setRequirement(requirement);

        return builder.build();
    }

    @Override
    public JsonElement serialize(Seed src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("tier", src.getTier());
        jsonObject.addProperty("amount", src.getAmount());
        jsonObject.addProperty("input", src.getInput());
        jsonObject.add("output", context.serialize(src.getOutput()));
        jsonObject.addProperty("color", "#" + Integer.toHexString(src.getColor().getRGB()).substring(2).toUpperCase());
        if (!src.getChance().equals(DEFAULT_CHANCE))
            jsonObject.add("chance", context.serialize(src.getChance()));
        if (!src.getRequirement().equals(DEFAULT_REQUIREMENT))
            jsonObject.add("requirement", context.serialize(src.getRequirement()));

        return jsonObject;
    }
}
