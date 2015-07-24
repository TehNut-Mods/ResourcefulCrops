package tehnut.resourceful.crops.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.util.cache.PermanentCache;

public class ResourcefulAPI {

    public static Item seed;
    public static Item shard;
    public static Item pouch;
    public static Item stone;
    public static Item material;

    public static Block crop;
    public static Block ore;

    public static PermanentCache<Seed> seedCache;
}
