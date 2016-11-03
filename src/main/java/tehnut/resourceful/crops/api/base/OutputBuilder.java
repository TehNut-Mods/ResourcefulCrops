package tehnut.resourceful.crops.api.base;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.item.ItemStack;

/**
 * Factory for creating SeedReqs. Documentation for each field can be found in
 * {@link Requirement}
 */
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class OutputBuilder {
    private ItemStack outputStack;
    private String    recipe = "default";

    public Output build() {
        return new Output(outputStack, recipe);
    }
}
