package tehnut.resourceful.crops.compat.thaumcraft;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.crops.compat.ICompatibility;
import tehnut.resourceful.crops.registry.BlockRegistry;

public class CompatibilityThaumcraft implements ICompatibility {

    @Override
    public void loadCompatibility() {
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(BlockRegistry.getBlock(BlockRCrop.class), 1, 7));
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
