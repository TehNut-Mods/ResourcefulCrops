package tehnut.resourceful.crops.compat.neotech;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.repack.tehnut.lib.iface.ICompatibility;

public class CompatNeoTech implements ICompatibility {

    @Override
    public void loadCompatibility(InitializationPhase phase) {
        if (phase == InitializationPhase.PRE_INIT && ConfigHandler.blacklistNeoTechAccelerator)
            FMLInterModComms.sendMessage("neotech", "blacklistFertilizer", Block.blockRegistry.getNameForObject(BlockRegistry.getBlock(BlockRCrop.class)).toString());
    }

    @Override
    public String getModId() {
        return "neotech";
    }

    @Override
    public boolean enableCompat() {
        return true;
    }
}
