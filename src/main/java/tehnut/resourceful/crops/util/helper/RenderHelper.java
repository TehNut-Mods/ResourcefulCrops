package tehnut.resourceful.crops.util.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tehnut.resourceful.crops.ModInformation;

public class RenderHelper {

    public static void inventoryItemRender(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(ModInformation.ID + ":" + item.getClass().getSimpleName(), "inventory"));
    }

    public static void inventoryItemRender(Item item, int meta) {
        ModelBakery.addVariantName(item, ModInformation.ID + ":" + item.getClass().getSimpleName() + meta);
    }

    public static void inventoryItemRenderAll(Item item) {
        final Item renderItem = item;

        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(ModInformation.ID + ":" + renderItem.getClass().getSimpleName(), "inventory");
            }
        });
    }
}
