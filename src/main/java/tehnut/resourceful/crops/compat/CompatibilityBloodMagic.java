package tehnut.resourceful.crops.compat;

import WayofTime.bloodmagic.api.BloodMagicAPI;
import WayofTime.bloodmagic.api.registry.HarvestRegistry;
import tehnut.resourceful.crops.core.ConfigHandler;
import tehnut.resourceful.crops.core.ModObjects;
import tehnut.resourceful.crops.core.recipe.ShapedSeedRecipe;

import java.lang.reflect.Field;
import java.util.List;

@Compatibility(modid = "BloodMagic")
public class CompatibilityBloodMagic implements ICompatibility {

    @Override
    public void loadCompatibility() {
        BloodMagicAPI.blacklistFromGreenGrove(ModObjects.CROP);
        if (ConfigHandler.compatibility.enableBloodMagicAutomation)
            HarvestRegistry.registerStandardCrop(ModObjects.CROP, 7);

        try {
            Class storageBlockAssimilatorClass = Class.forName("WayofTime.bloodmagic.compress.StorageBlockCraftingRecipeAssimilator");
            Field ignoreField = storageBlockAssimilatorClass.getDeclaredField("ignore");
            ignoreField.setAccessible(true);
            List<Class> ignore = (List<Class>) ignoreField.get(null);
            ignore.add(ShapedSeedRecipe.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
