package tehnut.resourceful.crops.core.recipe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tehnut.resourceful.crops.core.data.SeedStack;
import tehnut.resourceful.crops.item.ItemResourceful;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class RecipeHelper {

    @Nonnull
    public static ShapedOreRecipe newShaped(ItemStack output, CraftingHelper.ShapedPrimer input) {
        return new ShapedOreRecipe(output.getItem().getRegistryName(), output, input);
    }

    @Nonnull
    public static ShapedOreRecipe newShaped(ItemStack output, Object... input) {
        return newShaped(output, parseShaped(input));
    }

    @Nonnull
    public static ShapedOreRecipe newShaped(SeedStack output, CraftingHelper.ShapedPrimer input) {
        return newShaped(ItemResourceful.getResourcefulStack(output), input);
    }

    @Nonnull
    public static ShapedOreRecipe newShaped(SeedStack output, Object... input) {
        return newShaped(ItemResourceful.getResourcefulStack(output), parseShaped(input));
    }

    // Copied from CraftingHelper.parseShaped() with a single line modified to handle custom Ingredient types
    public static CraftingHelper.ShapedPrimer parseShaped(Object... recipe) {
        CraftingHelper.ShapedPrimer ret = new CraftingHelper.ShapedPrimer();
        StringBuilder shape = new StringBuilder();
        int idx = 0;

        if (recipe[idx] instanceof Boolean) {
            ret.mirrored = (Boolean) recipe[idx];
            if (recipe[idx + 1] instanceof Object[])
                recipe = (Object[]) recipe[idx + 1];
            else
                idx = 1;
        }

        if (recipe[idx] instanceof String[]) {
            String[] parts = ((String[]) recipe[idx++]);

            for (String s : parts) {
                ret.width = s.length();
                shape.append(s);
            }

            ret.height = parts.length;
        } else {
            while (recipe[idx] instanceof String) {
                String s = (String) recipe[idx++];
                shape.append(s);
                ret.width = s.length();
                ret.height++;
            }
        }

        if (ret.width * ret.height != shape.length() || shape.length() == 0) {
            StringBuilder err = new StringBuilder("Invalid shaped recipe: ");
            for (Object tmp : recipe) {
                err.append(tmp).append(", ");
            }
            throw new RuntimeException(err.toString());
        }

        HashMap<Character, Ingredient> itemMap = Maps.newHashMap();
        itemMap.put(' ', Ingredient.EMPTY);

        for (; idx < recipe.length; idx += 2) {
            Character chr = (Character) recipe[idx];
            Object in = recipe[idx + 1];
            Ingredient ing = getIngredient(in); // Modified to use custom getIngredient() method to support custom Ingredient types

            if (' ' == chr)
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");

            if (ing != null) {
                itemMap.put(chr, ing);
            } else {
                StringBuilder err = new StringBuilder("Invalid shaped ore recipe: ");
                for (Object tmp : recipe) {
                    err.append(tmp).append(", ");
                }
                throw new RuntimeException(err.toString());
            }
        }

        ret.input = NonNullList.withSize(ret.width * ret.height, Ingredient.EMPTY);

        Set<Character> keys = Sets.newHashSet(itemMap.keySet());
        keys.remove(' ');

        int x = 0;
        for (char chr : shape.toString().toCharArray()) {
            Ingredient ing = itemMap.get(chr);
            if (ing == null)
                throw new IllegalArgumentException("Pattern references symbol '" + chr + "' but it's not defined in the key");
            ret.input.set(x++, ing);
            keys.remove(chr);
        }

        if (!keys.isEmpty())
            throw new IllegalArgumentException("Key defines symbols that aren't used in pattern: " + keys);

        return ret;
    }

    @Nullable
    public static Ingredient getIngredient(Object object) {
        Ingredient ingredient = CraftingHelper.getIngredient(object);

        if (ingredient == null) {
            if (object instanceof SeedStack)
                ingredient = new IngredientSeed((SeedStack) object);
            else if (object instanceof Collection) {
                Collection collection = (Collection) object;
                if (!collection.isEmpty()) {
                    Class first = getCollectionType(collection);
                    if (first == ItemStack.class)
                        ingredient = Ingredient.fromStacks(((Collection<ItemStack>) collection).toArray(new ItemStack[collection.size()]));
                    else if (first == SeedStack.class)
                        ingredient = new IngredientSeed(((Collection<SeedStack>) collection).toArray(new SeedStack[collection.size()]));
                    else
                        ingredient = Ingredient.EMPTY;
                } else ingredient = Ingredient.EMPTY;
            }
        }

        return ingredient;
    }

    @Nullable
    private static Class getCollectionType(@Nonnull Collection<?> collection) {
        for (Object thing : collection)
            return thing.getClass();

        return null;
    }
}
