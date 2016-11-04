package tehnut.resourceful.crops.api.base;

import lombok.EqualsAndHashCode;
import net.minecraft.item.ItemStack;

@EqualsAndHashCode
public class Output {
    private ItemStack outputStack;
    private String recipe;

    /**
     * @param outputStack
     * @param recipe
     */
    public Output(ItemStack outputStack, String recipe) {
        this.outputStack = outputStack;
        this.recipe = recipe;
    }

    public String[] getRecipe() {
        return Recipe.parseRecipe(recipe);
    }

    @Override
    public String toString() {
        return "Output{" + "outputStack='" + outputStack + '\'' + ", recipe='" + recipe + '\'' + '}';
    }

    public ItemStack getOutputStack() {
        return outputStack;
    }

}
