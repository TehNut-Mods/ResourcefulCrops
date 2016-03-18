package tehnut.resourceful.crops.registry;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.repack.tehnut.lib.annot.ModItem;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {

    private static Map<Class<? extends Item>, String> classToName = new HashMap<Class<? extends Item>, String>();

    public static void init() {
        for (ASMDataTable.ASMData data : ResourcefulCrops.instance.modItems) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());
                Class<? extends Item> modItemClass = asmClass.asSubclass(Item.class);
                String name = modItemClass.getAnnotation(ModItem.class).name();

                Item modItem = modItemClass.newInstance();

                GameRegistry.registerItem(modItem, name);
                ResourcefulCrops.proxy.tryHandleItemModel(modItem, name);
                classToName.put(modItemClass, name);
            } catch (Exception e) {
                ResourcefulAPI.logger.error("Unable to register item for class {}", data.getClassName());
            }
        }
    }

    public static Item getItem(String name) {
        return GameRegistry.findItem(ModInformation.ID, name);
    }

    public static Item getItem(Class<? extends Item> itemClass) {
        return getItem(classToName.get(itemClass));
    }

    public static String getName(Class<? extends Item> itemClass) {
        return classToName.get(itemClass);
    }
}
