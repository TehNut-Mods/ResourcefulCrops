package tehnut.resourceful.crops.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.items.*;

public class ItemRegistry {

    public static ItemSeed seed;
    public static Item shard;
    public static Item pouch;
    public static Item stone;
    public static Item material;

    public static void registerItems() {
        stone = new ItemStone();
        GameRegistry.registerItem(stone, "ItemStone");

        material = new ItemMaterial();
        GameRegistry.registerItem(material, "ItemMaterial");

        seed = new ItemSeed();
        GameRegistry.registerItem(seed, "ItemSeed");

        shard = new ItemShard();
        GameRegistry.registerItem(shard, "ItemShard");

        if (ConfigHandler.enableSeedPouches) {
            pouch = new ItemPouch();
            GameRegistry.registerItem(pouch, "ItemPouch");
        }
    }
}
