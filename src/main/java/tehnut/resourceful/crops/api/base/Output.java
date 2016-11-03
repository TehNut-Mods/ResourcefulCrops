package tehnut.resourceful.crops.api.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import net.minecraft.item.ItemStack;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.api.ResourcefulAPI;

@EqualsAndHashCode
public class Output {
    private ItemStack            outputStack;
    private String               recipe;
    private static final Pattern recipePattern = Pattern.compile("^[S #]{5,11}$");
    private static final Pattern linePattern   = Pattern.compile("^[S ]{2,3}$");

    /**
     * @param outputStack
     * @param recipe
     */
    public Output(ItemStack outputStack, String recipe) {
        this.outputStack = outputStack;
        this.recipe = recipe;
    }

    private String[] translateRecipe(String... recipe) {
        if (recipe == null || recipe.length == 0) {
            ResourcefulAPI.logger.info("No recipe found, using 'default': " + this.toString());
            return translateRecipe("default");
        }
        switch (recipe.length) {
            case 1:
                if (recipe[0] == null) {
                    ResourcefulAPI.logger.info("No recipe found, using 'default': " + this.toString());
                    return translateRecipe("default");
                }
                if ("default".equals(recipe[0])) {
                    return translateRecipe(ConfigHandler.defaultRecipe);
                }
                if ("2x2".equals(recipe[0])) {
                    return translateRecipe("SS#SS");
                }
                if ("3x3".equals(recipe[0])) {
                    return translateRecipe("SSS#SSS#SSS");
                }
                if ("chest".equals(recipe[0])) {
                    return translateRecipe("SSS#S S#SSS");
                }
                if ("cross".equals(recipe[0])) {
                    return translateRecipe(" S #SSS# S ");
                }
                if (recipePattern.matcher(recipe[0]).matches()) {
                    return translateRecipe(recipe[0].split("#", 3));
                }
                // fallback if default recipe is broken
                ResourcefulAPI.logger.info("No valid recipe found, using 'chest': " + this.toString());
                return translateRecipe("chest");
            case 2:
                for (String line : recipe) {
                    Matcher m = linePattern.matcher(line);
                    if (!(m.matches() && line.length() == 2)) {
                        ResourcefulAPI.logger.info("Illegal recipe found, using 'default': " + this.toString());
                        return translateRecipe("default");
                    }
                }
            case 3:
                for (String line : recipe) {
                    Matcher m = linePattern.matcher(line);
                    if (!(m.matches() && line.length() == 3)) {
                        ResourcefulAPI.logger.info("Illegal recipe found, using 'default': " + this.toString());
                        return translateRecipe("default");
                    }
                }
        }
        return recipe;
    }

    public String[] getRecipe() {
        return translateRecipe(recipe);
    }

    @Override
    public String toString() {
        return "Output{" + "outputStack='" + outputStack + '\'' + ", recipe='" + recipe + '\'' + '}';
    }

    public ItemStack getOutputStack() {
        return outputStack;
    }

}
