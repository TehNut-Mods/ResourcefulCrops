package tehnut.resourceful.crops.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import tehnut.resourceful.crops.core.RegistrarResourcefulCrops;
import tehnut.resourceful.crops.core.data.InfoOverride;
import tehnut.resourceful.crops.core.data.Seed;
import tehnut.resourceful.crops.item.ItemResourceful;

@SuppressWarnings("ConstantConditions")
public class ResourcefulMeshDefinition implements ItemMeshDefinition {

    private final String baseName;

    public ResourcefulMeshDefinition(ItemResourceful item) {
        this.baseName = item.getBaseName();

        ModelLoader.registerItemVariants(item, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        for (Seed seed : RegistrarResourcefulCrops.SEEDS) {
            InfoOverride.ModelInfo modelInfo = seed.getOverrides().getModel(baseName);
            if (modelInfo != null)
                ModelLoader.registerItemVariants(item, new ModelResourceLocation(modelInfo.getPath(), modelInfo.getVariant()));
        }
    }

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack) {
        if (!(stack.getItem() instanceof ItemResourceful))
            return new ModelResourceLocation(stack.getItem().getRegistryName(), "inventory");

        Seed seed = ((ItemResourceful) stack.getItem()).getSeed(stack);
        InfoOverride.ModelInfo modelInfo = seed.getOverrides().getModel(baseName);
        if (modelInfo == null)
            return new ModelResourceLocation(stack.getItem().getRegistryName(), "inventory");

        ModelResourceLocation location = new ModelResourceLocation(modelInfo.getPath(), modelInfo.getVariant());
        IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(location);
        if (model == null || model.getClass().getCanonicalName().contains("FancyMissingModel"))
            return new ModelResourceLocation(stack.getItem().getRegistryName(), "inventory");

        return location;
    }
}
