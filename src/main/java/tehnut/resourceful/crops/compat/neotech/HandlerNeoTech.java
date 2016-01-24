package tehnut.resourceful.crops.compat.neotech;

import com.dyonovan.neotech.registries.FertilizerBlacklistRegistry;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.crops.registry.BlockRegistry;

public class HandlerNeoTech {

    public static void blacklistBlocks() {
        FertilizerBlacklistRegistry.addToBlacklist(BlockRegistry.getBlock(BlockRCrop.class));
    }
}
