package tehnut.resourceful.crops.core.data;

import com.google.common.base.Strings;
import net.minecraft.item.ItemStack;
import tehnut.resourceful.crops.core.ConfigHandler;

import javax.annotation.Nullable;
import java.util.Locale;

public class Output {

    private final ItemStack item;
    private final Shape shape;
    @Nullable
    private final String customFormat;

    public Output(ItemStack item, Shape shape, @Nullable String customFormat) {
        this.item = item;
        this.shape = shape;
        this.customFormat = customFormat;
    }

    public ItemStack getItem() {
        return item;
    }

    public Shape getShape() {
        return shape;
    }

    @Nullable
    public String getCustomFormat() {
        return customFormat;
    }

    public enum Shape {

        DEFAULT(ConfigHandler.crafting.defaultRecipeShape),
        CHEST("SSS#S S#SSS"),
        CROSS(" S #SSS# S "),
        TWO_BY_TWO("SS#SS"),
        THREE_BY_THREE("SSS#SSS#SSS"),
        CUSTOM("")
        ;

        private String recipeFormat;

        Shape(String recipeFormat) {
            this.recipeFormat = recipeFormat;
        }

        public String getRecipeFormat() {
            return recipeFormat;
        }

        public static String[] parseRecipe(String recipe, @Nullable String customFormat) {
            try {
                Shape shape = Shape.valueOf(recipe.toUpperCase(Locale.ENGLISH));
                if (shape == Shape.CUSTOM)
                    recipe = !Strings.isNullOrEmpty(customFormat) ? customFormat : DEFAULT.getRecipeFormat();
                else
                    recipe = shape.getRecipeFormat();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return recipe.split("#", 3);
        }
    }
}
