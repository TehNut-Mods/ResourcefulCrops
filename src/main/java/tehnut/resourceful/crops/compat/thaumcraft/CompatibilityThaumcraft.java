package tehnut.resourceful.crops.compat.thaumcraft;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.lib.iface.ICompatibility;
import tehnut.lib.util.helper.BlockHelper;
import tehnut.resourceful.crops.block.BlockRCrop;

public class CompatibilityThaumcraft implements ICompatibility {

    @Override
    public void loadCompatibility(InitializationPhase phase) {
        if (phase == InitializationPhase.PRE_INIT)
            FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(BlockHelper.getBlock(BlockRCrop.class), 1, 7));
    }

    @Override
    public String getModId() {
        return "Thaumcraft";
    }

    @Override
    public boolean enableCompat() {
        return true;
    }
}
