package tehnut.resourceful.crops2.compat.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import tehnut.resourceful.crops2.core.recipe.ShapedListRecipe;

import java.util.List;

public class ShapedListRecipeHandler implements IRecipeHandler<ShapedListRecipe> {

    @Override
    public Class<ShapedListRecipe> getRecipeClass() {
        return ShapedListRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public String getRecipeCategoryUid(ShapedListRecipe recipe) {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(ShapedListRecipe recipe) {
        return new ShapedListRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(ShapedListRecipe recipe) {
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
