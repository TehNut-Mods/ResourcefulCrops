package tehnut.resourceful.crops.proxy;

import com.google.common.base.Stopwatch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.block.tile.TileSeedContainer;
import tehnut.resourceful.crops.core.RegistrarResourcefulCrops;
import tehnut.resourceful.crops.core.data.Seed;
import tehnut.resourceful.crops.item.ItemResourceful;
import tehnut.resourceful.crops.util.Util;

import java.awt.Color;

@SideOnly(Side.CLIENT)
@SuppressWarnings("ConstantConditions")
public class ClientProxy extends CommonProxy {

    private static boolean firstCheck = false;
    private static final IResourceManagerReloadListener COLOR_RELOADER = resourceManager -> {
        if (!firstCheck) {
            firstCheck = true;
            return;
        }

        Stopwatch stopwatch = Stopwatch.createStarted();
        int count = 0;
        for (Seed seed : RegistrarResourcefulCrops.SEEDS) {
            if (!RegistrarResourcefulCrops.SEED_DEFAULT.equals(seed.getRegistryName()) && seed.shouldGenerateColor()) {
                ResourcefulCrops.debug("Generating color for seed {}", seed.getRegistryName());
                ItemStack base = seed.getInputItems().get(0);
                Color color = new Color(Util.getStackColor(base)).brighter();
                seed.setColor(color);
                ResourcefulCrops.debug("Generated color {} for seed {} based on the stack {}", color.getRGB(), seed.getRegistryName(), base);
                count++;
            }
        }
        ResourcefulCrops.LOGGER.info("Generated colors for {} seeds in {}", count, stopwatch.stop());
    };

    @Override
    public void init() {
        super.init();

        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(COLOR_RELOADER);

        Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            Seed seed = ((ItemResourceful) stack.getItem()).getSeed(stack);
            if (seed == null || (stack.getItem() == RegistrarResourcefulCrops.POUCH && tintIndex != 1))
                return -1;

            return seed.getColor().getRGB();
        }, RegistrarResourcefulCrops.SEED, RegistrarResourcefulCrops.POUCH, RegistrarResourcefulCrops.SHARD);

        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, blockAccess, pos, tintIndex) -> {
            if (blockAccess == null || pos == null)
                return -1;
            TileSeedContainer seedContainer = Util.getSeedContainer(blockAccess, pos);
            if (seedContainer == null)
                return -1;

            Seed seed = RegistrarResourcefulCrops.SEEDS.getValue(seedContainer.getSeedKey());
            if (seed == null)
                return -1;

            return seed.getColor().getRGB();
        }, RegistrarResourcefulCrops.CROP);
    }

    @Override
    public void postInit() {
        super.postInit();
    }
}
