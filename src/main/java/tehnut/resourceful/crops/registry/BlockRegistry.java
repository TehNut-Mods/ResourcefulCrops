package tehnut.resourceful.crops.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.annot.ModBlock;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.crops.block.BlockROre;
import tehnut.resourceful.crops.util.helper.LogHelper;
import tehnut.resourceful.crops.util.helper.RenderHelper;

public class BlockRegistry {

    public static void init() {
        for (ASMDataTable.ASMData data : ResourcefulCrops.instance.modBlocks) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());

                Class<? extends Block> modBlockClass = asmClass.asSubclass(Block.class);
                Class<? extends TileEntity> tileClass = modBlockClass.getAnnotation(ModBlock.class).tileEntity();
                Class<? extends ItemBlock> itemBlockClass = modBlockClass.getAnnotation(ModBlock.class).itemBlock();

                Block modBlock = modBlockClass.newInstance();

                registerBlock(modBlock, itemBlockClass);
                registerTile(tileClass);

            } catch (Exception e) {
                LogHelper.error(String.format("Unable to register block for class %s", data.getClassName()));
            }
        }
    }

    private static void registerBlock(Block block, Class<? extends ItemBlock> itemBlock) {
        GameRegistry.registerBlock(block, itemBlock, block.getClass().getSimpleName());
    }

    private static void registerTile(Class<? extends TileEntity> tile) {
        if (tile != TileEntity.class)
            GameRegistry.registerTileEntity(tile, ModInformation.ID + ":" + tile.getSimpleName());
    }

    public static Block getBlock(Class<? extends Block> blockClass) {
        return ResourcefulAPI.getBlock(blockClass.getSimpleName());
    }

    public static void registerRenders() {
        RenderHelper.inventoryItemRender(Item.getItemFromBlock(getBlock(BlockROre.class)));
        RenderHelper.inventoryItemRender(Item.getItemFromBlock(getBlock(BlockROre.class)), 1);
        RenderHelper.inventoryItemRender(Item.getItemFromBlock(getBlock(BlockRCrop.class)), 0, "BlockRCrop");
    }
}
