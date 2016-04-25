package tehnut.resourceful.crops.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.registry.PersistentRegistryManager;
import org.apache.logging.log4j.Logger;
import tehnut.resourceful.crops.api.base.Seed;

import javax.annotation.Nullable;
import java.util.Map;

public class ResourcefulAPI {

    public static final String SEED = "ItemSeed";
    public static final String SHARD = "ItemShard";
    public static final String MATERIAL = "ItemMaterial";
    public static final String STONE = "ItemStone";
    public static final String POUCH = "ItemPouch";

    public static final String CROP = "BlockRCrop";
    public static final String ORE = "BlockROre";
    public static Logger logger;

    public static final FMLControlledNamespacedRegistry<Seed> SEEDS = PersistentRegistryManager.createRegistry(
            new ResourceLocation(ModInformation.ID, "seeds"),
            Seed.class,
            null,
            0,
            512,
            false,
            new IForgeRegistry.AddCallback<Seed>() {
                @Override
                public void onAdd(Seed obj, int id, Map<ResourceLocation, ?> slaveset) {
                    // No-op
                }
            },
            new IForgeRegistry.ClearCallback<Seed>() {
                @Override
                public void onClear(Map<ResourceLocation, ?> slaveset) {
                    // No-op
                }
            },
            new IForgeRegistry.CreateCallback<Seed>() {
                @Override
                public void onCreate(Map<ResourceLocation, ?> slaveset) {
                    // No-op
                }
            }
    );

    /**
     * An abstracted way to get an {@link Item} from ResourcefulCrops. Use the final constant
     * fields above in case names change.
     *
     * @param name - The registered name of the Item
     * @return - The item mapped to the registered name.
     */
    @Nullable
    public static Item getItem(String name) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(ModInformation.ID, name));
    }

    /**
     * An abstracted way to get a {@link Block} from ResourcefulCrops. Use the final constant
     * fields above in case names change.
     *
     * @param name - The registered name of the Block
     * @return - The Block mapped to the registered name.
     */
    @Nullable
    public static Block getBlock(String name) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ModInformation.ID, name));
    }
}
