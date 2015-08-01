package tehnut.resourceful.crops.util.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import tehnut.resourceful.crops.api.ModInformation;

public class RenderHelper {

    public static void inventoryItemRender(Item item, int meta, String name) {
        if (item instanceof ItemBlock && name.startsWith("ItemBlock"))
            name = name.replace("Item", "");

        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        String resName = ModInformation.RESLOC + name;

        ModelBakery.addVariantName(item, resName);
        renderItem.getItemModelMesher().register(item, meta, new ModelResourceLocation(resName, "inventory"));
    }

    public static void inventoryItemRender(Item item, int meta) {
        inventoryItemRender(item, meta, item.getClass().getSimpleName() + meta);
    }

    public static void inventoryItemRender(Item item) {
        inventoryItemRender(item, 0, item.getClass().getSimpleName());
    }

    public static void inventoryItemRenderAll(Item item) {
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        final Item toRender = item;

        renderItem.getItemModelMesher().register(item, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(ModInformation.RESLOC + toRender.getClass().getSimpleName(), "inventory");
            }
        });
    }
}
