package tehnut.shh;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

@Mod(modid = Shh.MODID, name = Shh.MODID, version = "1.0.0", clientSideOnly = true)
public class Shh {

    public static final String MODID = "shh";
    public static final Logger LOGGER = LogManager.getLogger("Shh");

    public static String[] domains = new String[]{};

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        File jsonConfig = new File(event.getModConfigurationDirectory(), "shh.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
        try {
            if (!jsonConfig.exists() && jsonConfig.createNewFile()) {
                FileWriter fileWriter = new FileWriter(jsonConfig);
                fileWriter.write(gson.toJson(new String[]{}));
                fileWriter.close();
            }

            domains = gson.fromJson(new FileReader(jsonConfig), String[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (domains.length == 1 && domains[0].equalsIgnoreCase("*")) {
            List<String> allMods = Lists.newArrayList("minecraft");
            for (ModContainer modContainer : Loader.instance().getActiveModList())
                allMods.add(modContainer.getModId().toLowerCase(Locale.ENGLISH));
            domains = allMods.toArray(new String[allMods.size()]);
        }

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Map<ResourceLocation, Exception> modelErrors = ReflectionHelper.getPrivateValue(ModelLoader.class, event.getModelLoader(), "loadingExceptions");

        int totalErrored = 0;

        for (String domain : domains) {
            // Collect model errors for domain
            List<ResourceLocation> errored = new ArrayList<ResourceLocation>();
            for (ResourceLocation modelError : modelErrors.keySet())
                if (modelError.getResourceDomain().equalsIgnoreCase(domain))
                    errored.add(modelError);

            // Remove discovered model errors
            for (ResourceLocation modelError : errored)
                //noinspection ThrowableResultOfMethodCallIgnored
                modelErrors.remove(modelError);

            if (errored.size() > 0)
                LOGGER.info("[{}] Suppressed {} model errors", domain, errored.size());

            totalErrored += errored.size();
        }

        LOGGER.info("[Total] Suppressed {} model errors in {}", totalErrored, stopwatch.stop());
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Post event) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        SetMultimap<String, ResourceLocation> missingTextures = ReflectionHelper.getPrivateValue(FMLClientHandler.class, FMLClientHandler.instance(), "missingTextures");
        Set<String> badTextureDomains = ReflectionHelper.getPrivateValue(FMLClientHandler.class, FMLClientHandler.instance(), "badTextureDomains");

        int totalTextures = 0;

        for (String domain : domains) {
            Set<ResourceLocation> toRemove = new HashSet<ResourceLocation>();

            // Find our missing textures and mark them for removal. Cannot directly remove as it would cause a CME
            if (missingTextures.containsKey(domain))
                for (ResourceLocation texture : missingTextures.get(domain))
                    toRemove.add(texture);

            // Remove all our found errors
            missingTextures.get(domain).removeAll(toRemove);

            if (missingTextures.get(domain).isEmpty()) {
                missingTextures.keySet().remove(domain);
                badTextureDomains.remove(domain);
            }

            if (toRemove.size() > 0)
                LOGGER.info("[{}] Suppressed {} texture errors", domain, toRemove.size());

            totalTextures += toRemove.size();
        }

        LOGGER.info("[Total] Suppressed {} texture errors in {}", totalTextures, stopwatch.stop());
    }
}
