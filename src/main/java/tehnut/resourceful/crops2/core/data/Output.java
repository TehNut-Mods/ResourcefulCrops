package tehnut.resourceful.crops2.core.data;

import com.google.common.base.Strings;
import net.minecraft.item.ItemStack;
import tehnut.resourceful.crops2.core.ConfigHandler;

import javax.annotation.Nullable;

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
            for (Shape shape : values()) {
                if (shape.name().equalsIgnoreCase(recipe)) {
                    if (shape == CUSTOM && !Strings.isNullOrEmpty(customFormat)) {
                        recipe = customFormat;
                        break;
                    } else if (shape == CUSTOM && Strings.isNullOrEmpty(customFormat)) {
                        recipe = DEFAULT.getRecipeFormat();
                        break;
                    }
                    recipe = shape.getRecipeFormat();
                    break;
                }
            }

            return recipe.split("#", 3);
        }
    }
}
