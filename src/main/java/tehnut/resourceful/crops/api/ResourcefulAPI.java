package tehnut.resourceful.crops.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public class ResourcefulAPI {

    public static final String SEED = "ItemSeed";
    public static final String SHARD = "ItemShard";
    public static final String MATERIAL = "ItemMaterial";
    public static final String STONE = "ItemStone";
    public static final String POUCH = "ItemPouch";

    public static final String CROP = "BlockRCrop";
    public static final String ORE = "BlockROre";
    public static Logger logger;

    /**
     * An abstracted way to get an {@link Item} from ResourcefulCrops. Use the final constant
     * fields above in case names change.
     *
     * @param name - The registered name of the Item
     * @return - The item mapped to the registered name.
     */
    @Nullable
    public static Item getItem(String name) {
        return GameRegistry.findItem(ModInformation.ID, name);
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
        return GameRegistry.findBlock(ModInformation.ID, name);
    }
}
