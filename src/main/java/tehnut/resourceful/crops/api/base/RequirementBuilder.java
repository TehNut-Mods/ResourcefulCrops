package tehnut.resourceful.crops.api.base;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.world.EnumDifficulty;
import tehnut.resourceful.crops.api.util.BlockStack;

/**
 * Factory for creating SeedReqs.
 * Documentation for each field can be found in {@link Requirement}
 */
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class RequirementBuilder {

    private BlockStack growthReq = null;
    private EnumDifficulty difficulty = EnumDifficulty.PEACEFUL;
    private int lightLevelMin = 9;
    private int lightLevelMax = 15;

    public Requirement build() {
        return new Requirement(growthReq, difficulty, lightLevelMin, lightLevelMax);
    }
}
