package tehnut.resourceful.crops.compat.neotech;

import com.dyonovan.neotech.managers.FertilizerBlacklistManager;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.crops.registry.BlockRegistry;

public class HandlerNeoTech {

    public static void blacklistBlocks() {
        FertilizerBlacklistManager.blacklistBlock(BlockRegistry.getBlock(BlockRCrop.class));
    }
}
