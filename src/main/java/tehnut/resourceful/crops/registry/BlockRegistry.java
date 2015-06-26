package tehnut.resourceful.crops.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import tehnut.resourceful.crops.ModInformation;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.crops.block.BlockROre;
import tehnut.resourceful.crops.item.block.ItemBlockROre;
import tehnut.resourceful.crops.tile.TileRCrop;

public class BlockRegistry {

    public static Block ore;
    public static Block crop;

    public static void registerBlocks() {
        ore = new BlockROre();
        GameRegistry.registerBlock(ore, ItemBlockROre.class, "BlockROre");

        crop = new BlockRCrop();
        GameRegistry.registerBlock(crop, "BlockRCrop");
        GameRegistry.registerTileEntity(TileRCrop.class, ModInformation.ID + ":TileRCrop");
    }
}
