package tehnut.resourceful.crops2.compat.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import tehnut.resourceful.crops2.core.recipe.ShapedListRecipe;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShapedListRecipeWrapper extends BlankRecipeWrapper implements IShapedCraftingRecipeWrapper {

    private final ShapedListRecipe recipe;
    private final int width;
    private final int height;

    public ShapedListRecipeWrapper(ShapedListRecipe recipe) {
        this.recipe = recipe;
        for (Object input : this.recipe.getInput()) {
            if (input instanceof ItemStack) {
                ItemStack itemStack = (ItemStack) input;
                if (itemStack.stackSize != 1)
                    itemStack.stackSize = 1;
            }
        }
        this.width = ObfuscationReflectionHelper.getPrivateValue(ShapedListRecipe.class, this.recipe, "width");
        this.height = ObfuscationReflectionHelper.getPrivateValue(ShapedListRecipe.class, this.recipe, "height");
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> inputs = ResourcefulCropsPlugin.stackHelper.expandRecipeItemStackInputs(Arrays.asList(recipe.getInput()));
        ingredients.setInputLists(ItemStack.class, inputs);

        ItemStack recipeOutput = recipe.getRecipeOutput();
        if (recipeOutput != null) {
            ingredients.setOutput(ItemStack.class, recipeOutput);
        }
    }

    @Override
    public List getInputs() {
        return Arrays.asList(recipe.getInput());
    }

    @Override
    public List<ItemStack> getOutputs() {
        return Collections.singletonList(recipe.getRecipeOutput());
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
