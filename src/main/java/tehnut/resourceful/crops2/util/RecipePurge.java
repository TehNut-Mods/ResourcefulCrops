package tehnut.resourceful.crops2.util;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Sets;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tehnut.resourceful.crops2.ResourcefulCrops2;
import tehnut.resourceful.crops2.core.recipe.ShapedListRecipe;
import tehnut.resourceful.crops2.item.ItemResourceful;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecipePurge {

    public static void purge() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Set<IRecipe> toRemoveCrafting = Sets.newHashSet();
        for (IRecipe recipe : CraftingManager.getInstance().getRecipeList()) {
            if (recipe.getRecipeOutput() != null && (recipe.getRecipeOutput().getItem() instanceof ItemResourceful))
                toRemoveCrafting.add(recipe);

            if (recipe instanceof ShapedOreRecipe)
                if (compareInputs(((ShapedOreRecipe) recipe).getInput()))
                    toRemoveCrafting.add(recipe);
            if (recipe instanceof ShapelessOreRecipe)
                if (compareInputs(((ShapelessOreRecipe) recipe).getInput().toArray(new Object[((ShapelessOreRecipe) recipe).getInput().size()])))
                    toRemoveCrafting.add(recipe);
            if (recipe instanceof ShapedListRecipe)
                if (compareInputs(((ShapedListRecipe) recipe).getInput()))
                    toRemoveCrafting.add(recipe);
        }

        Set<Map.Entry<ItemStack, ItemStack>> toRemoveSmelting = Sets.newHashSet();
        for (Map.Entry<ItemStack, ItemStack> smelting : FurnaceRecipes.instance().getSmeltingList().entrySet()) {
            if (smelting.getKey() != null && (smelting.getKey().getItem() instanceof ItemResourceful))
                toRemoveSmelting.add(smelting);

            if (smelting.getValue() != null && (smelting.getValue().getItem() instanceof ItemResourceful))
                toRemoveSmelting.add(smelting);
        }

        int totalRecipes = CraftingManager.getInstance().getRecipeList().size() + FurnaceRecipes.instance().getSmeltingList().size();
        int ourRecipes = toRemoveCrafting.size() + toRemoveSmelting.size();

        for (IRecipe remove : toRemoveCrafting)
            CraftingManager.getInstance().getRecipeList().remove(remove);
        for (Map.Entry<ItemStack, ItemStack> remove : toRemoveSmelting)
            FurnaceRecipes.instance().getSmeltingList().remove(remove.getKey());
        ResourcefulCrops2.LOGGER.info("Purged {} (out of {}) old mapping-specific recipes in {}", ourRecipes, totalRecipes, stopwatch.stop());
    }

    private static boolean compareInputs(Object[] inputs) {
        boolean ret = false;
        for (Object input : inputs) {
            if (input instanceof ItemStack)
                ret = ((ItemStack) input).getItem() instanceof ItemResourceful;
            else if (input instanceof List)
                for (ItemStack stack : (List<ItemStack>) input)
                    ret = stack.getItem() instanceof ItemResourceful;
            else if (input instanceof ItemResourceful)
                ret = true;
        }

        return ret;
    }
}
