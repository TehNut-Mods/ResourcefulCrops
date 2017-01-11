package tehnut.resourceful.crops.compat.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import tehnut.resourceful.crops.core.recipe.ShapedSeedRecipe;

import java.util.List;

public class ShapedSeedRecipeHandler implements IRecipeHandler<ShapedSeedRecipe> {

    @Override
    public Class<ShapedSeedRecipe> getRecipeClass() {
        return ShapedSeedRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public String getRecipeCategoryUid(ShapedSeedRecipe recipe) {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(ShapedSeedRecipe recipe) {
        return new ShapedSeedRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(ShapedSeedRecipe recipe) {
        if (recipe.getRecipeOutput() == null)
            return false;
        int inputCount = 0;
        for (Object input : recipe.getInput()) {
            if (input instanceof List) {
                if (((List) input).isEmpty()) {
                    // missing items for an oreDict name. This is normal behavior, but the recipe is invalid.
                    return false;
                }
            }
            if (input != null)
                inputCount++;
        }
        if (inputCount > 9)
            return false;
        if (inputCount == 0)
            return false;
        return true;
    }
}
