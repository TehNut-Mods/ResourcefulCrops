package tehnut.resourceful.crops.registry;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.item.*;
import tehnut.resourceful.crops.util.helper.RenderHelper;
import tehnut.resourceful.repack.tehnut.lib.annot.ModItem;

public class ItemRegistry {

    public static void init() {
        for (ASMDataTable.ASMData data : ResourcefulCrops.instance.modItems) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());
                Class<? extends Item> modItemClass = asmClass.asSubclass(Item.class);
                String name = modItemClass.getAnnotation(ModItem.class).name();
                Item modItem = modItemClass.newInstance();
                registerItem(modItem, name);
            } catch (Exception e) {
                ResourcefulAPI.logger.error(String.format("Unable to register item for class %s", data.getClassName()));
            }
        }
    }

    private static Item registerItem(Item item, String name) {
        GameRegistry.registerItem(item, name);
        return item;
    }

    public static Item getItem(Class<? extends Item> itemClass) {
        return ResourcefulAPI.getItem(itemClass.getSimpleName());
    }

    public static void registerRenders() {
        RenderHelper.inventoryItemRenderAll(getItem(ItemStone.class));
        RenderHelper.inventoryItemRenderAll(getItem(ItemShard.class));
        RenderHelper.inventoryItemRenderAll(getItem(ItemSeed.class));
        RenderHelper.inventoryItemRenderAll(getItem(ItemPouch.class));
        RenderHelper.inventoryItemRender(getItem(ItemMaterial.class));
        RenderHelper.inventoryItemRender(getItem(ItemMaterial.class), 1);
        RenderHelper.inventoryItemRender(getItem(ItemMaterial.class), 2);
        RenderHelper.inventoryItemRender(getItem(ItemMaterial.class), 3);
        RenderHelper.inventoryItemRender(getItem(ItemMaterial.class), 4);
    }
}
