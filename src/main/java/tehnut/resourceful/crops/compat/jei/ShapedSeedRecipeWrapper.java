package tehnut.resourceful.crops.compat.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import tehnut.resourceful.crops.core.recipe.ShapedSeedRecipe;

import java.util.Arrays;
import java.util.List;

public class ShapedSeedRecipeWrapper extends BlankRecipeWrapper implements IShapedCraftingRecipeWrapper {

    private final ShapedSeedRecipe recipe;
    private final int width;
    private final int height;

    public ShapedSeedRecipeWrapper(ShapedSeedRecipe recipe) {
        this.recipe = recipe;
        for (Object input : this.recipe.getInput()) {
            if (input instanceof ItemStack) {
                ItemStack itemStack = (ItemStack) input;
                if (itemStack.getCount() != 1)
                    itemStack.setCount(1);
            }
        }
        this.width = ObfuscationReflectionHelper.getPrivateValue(ShapedSeedRecipe.class, this.recipe, "width");
        this.height = ObfuscationReflectionHelper.getPrivateValue(ShapedSeedRecipe.class, this.recipe, "height");
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> inputs = ResourcefulCropsPlugin.stackHelper.expandRecipeItemStackInputs(Arrays.asList(recipe.getInput()));
        ingredients.setInputLists(ItemStack.class, inputs);
        ingredients.setOutput(ItemStack.class, recipe.getRecipeOutput());
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
