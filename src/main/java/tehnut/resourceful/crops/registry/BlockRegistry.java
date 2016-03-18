package tehnut.resourceful.crops.registry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.repack.tehnut.lib.annot.ModBlock;

import java.util.HashMap;
import java.util.Map;

public class BlockRegistry {

    private static Map<Class<? extends Block>, String> classToName = new HashMap<Class<? extends Block>, String>();

    public static void init() {
        for (ASMDataTable.ASMData data : ResourcefulCrops.instance.modBlocks) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());

                Class<? extends Block> modBlockClass = asmClass.asSubclass(Block.class);
                String name = modBlockClass.getAnnotation(ModBlock.class).name();
                Class<? extends TileEntity> tileClass = modBlockClass.getAnnotation(ModBlock.class).tileEntity();
                Class<? extends ItemBlock> itemBlockClass = modBlockClass.getAnnotation(ModBlock.class).itemBlock();

                Block modBlock = modBlockClass.newInstance();

                GameRegistry.registerBlock(modBlock, itemBlockClass, name);
                GameRegistry.registerTileEntity(tileClass, ModInformation.ID + ":" + tileClass.getSimpleName());
                ResourcefulCrops.proxy.tryHandleBlockModel(modBlock, name);
                classToName.put(modBlockClass, name);
            } catch (Exception e) {
                ResourcefulAPI.logger.error("Unable to register block for class {}", data.getClassName());
            }
        }
    }

    public static Block getBlock(String name) {
        return GameRegistry.findBlock(ModInformation.ID, name);
    }

    public static Block getBlock(Class<? extends Block> blockClass) {
        return getBlock(classToName.get(blockClass));
    }

    public static String getName(Class<? extends Block> blockClass) {
        return classToName.get(blockClass);
    }
}
