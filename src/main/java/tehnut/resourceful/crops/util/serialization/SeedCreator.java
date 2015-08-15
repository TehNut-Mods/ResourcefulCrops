package tehnut.resourceful.crops.util.serialization;

import com.google.gson.*;
import org.apache.commons.io.filefilter.FileFilterUtils;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.base.*;
import tehnut.resourceful.crops.api.util.BlockStack;
import tehnut.resourceful.crops.util.serialization.serializers.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SeedCreator {

    public static void registerJsonSeeds(GsonBuilder gsonBuilder, File folder) {
        File[] files = folder.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));
        for (File file : files)
            SeedCreator.createSeedsFromJson(gsonBuilder, file);
    }

    public static void registerJsonSeeds(GsonBuilder gsonBuilder) {
        File folder = new File(ResourcefulCrops.getConfigDir().getPath() + "/seeds");

        File[] files = folder.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));
        for (File file : files)
            SeedCreator.createSeedsFromJson(gsonBuilder, file);
    }

    @SuppressWarnings("unchecked")
    public static List<Seed> createSeedsFromJson(GsonBuilder gsonBuilder, File file) {
        try {
            Gson gson = gsonBuilder.setPrettyPrinting().disableHtmlEscaping().serializeNulls().create();
            return gson.fromJson(new FileReader(file), ArrayList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void createJsonFromSeeds(GsonBuilder gsonBuilder, List list, String fileName) {
        try {
            Gson gson = gsonBuilder.setPrettyPrinting().disableHtmlEscaping().serializeNulls().create();
            String reverse = gson.toJson(list, List.class);
            FileWriter fw = new FileWriter(new File(ResourcefulCrops.getConfigDir().getPath() + "/seeds", fileName + ".json"));
            fw.write("{\n\"seeds\": " + reverse + "\n}");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createJsonFromSeeds(GsonBuilder gsonBuilder, List list) {
        createJsonFromSeeds(gsonBuilder, list, "PrintedSeeds");
    }

    public static void registerCustomSerializers(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(ArrayList.class, new CustomListJson());
        gsonBuilder.registerTypeAdapter(Seed.class, new CustomSeedJson());
        gsonBuilder.registerTypeAdapter(BlockStack.class, new CustomBlockStackJson());
        gsonBuilder.registerTypeAdapter(SeedReq.class, new CustomSeedReqJson());
        gsonBuilder.registerTypeAdapter(Chance.class, new CustomChanceJson());
        gsonBuilder.registerTypeAdapter(Compat.class, new CustomCompatJson());
        gsonBuilder.registerTypeAdapter(Compat.CompatExNihilio.class, new CustomCompatJson.CustomCompatExNihilioJson());
    }
}
