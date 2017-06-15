package tehnut.resourceful.crops.compat.jei;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import tehnut.resourceful.crops.core.recipe.ShapedSeedRecipe;

public class ShapedSeedRecipeFactory implements IRecipeWrapperFactory<ShapedSeedRecipe> {

    @Override
    public IRecipeWrapper getRecipeWrapper(ShapedSeedRecipe recipe) {
        return new ShapedSeedRecipeWrapper(recipe);
    }
}
