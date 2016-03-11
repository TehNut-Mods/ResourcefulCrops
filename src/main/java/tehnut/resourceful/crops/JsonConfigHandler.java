package tehnut.resourceful.crops;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.item.ItemStack;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.api.base.Chance;
import tehnut.resourceful.crops.api.base.Requirement;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.api.util.BlockStack;
import tehnut.resourceful.crops.util.StartupUtils;
import tehnut.resourceful.crops.util.json.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonConfigHandler {

    public static Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .setVersion(0.2)
            .registerTypeAdapter(Seed.class, new CustomSeedJson())
            .registerTypeAdapter(BlockStack.class, new CustomBlockStackJson())
            .registerTypeAdapter(ItemStack.class, new CustomItemStackJson())
            .registerTypeAdapter(Requirement.class, new CustomRequirementJson())
            .registerTypeAdapter(Chance.class, new CustomChanceJson())
            .create();

    public static void init(File jsonConfig) {
        try {
            if (!jsonConfig.exists() && jsonConfig.createNewFile()) {
                Map<Integer, Seed> defaultList = handleDefaults();
                String json = gson.toJson(defaultList, new TypeToken<Map<Integer, Seed>>(){ }.getType());
                FileWriter writer = new FileWriter(jsonConfig);
                writer.write(json);
                writer.close();
            }

            Map<Integer, Seed> seeds = gson.fromJson(new FileReader(jsonConfig), new TypeToken<Map<Integer, Seed>>(){ }.getType());

            for (Map.Entry<Integer, Seed> entry : seeds.entrySet())
                SeedRegistry.registerSeed(entry.getKey(), entry.getValue());
        } catch (IOException e) {
            ResourcefulAPI.logger.error("Failed to handle Seed configuration.");
        }
    }

    private static Map<Integer, Seed> handleDefaults() {
        StartupUtils.initDefaults();
        return StartupUtils.getDefaultSeeds();
    }
}
