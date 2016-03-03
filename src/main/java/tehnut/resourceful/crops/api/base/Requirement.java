package tehnut.resourceful.crops.api.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.minecraft.world.EnumDifficulty;
import tehnut.resourceful.crops.api.util.BlockStack;

@Getter
@ToString
@EqualsAndHashCode
public class Requirement {

    private BlockStack growthReq;
    private EnumDifficulty difficulty;
    private int lightLevelMin;
    private int lightLevelMax;

    /**
     * To create a seed requirement, use {@link RequirementBuilder}
     *
     * @param growthReq     - BlockStack needed under the soil for the crop to grow.
     * @param difficulty    - Minimum difficulty needed for the crop to grow.
     * @param lightLevelMin - Minimum light level required for a crop to grow.
     * @param lightLevelMax - Maximum light level the crop can grow at.
     */
    protected Requirement(BlockStack growthReq, EnumDifficulty difficulty, int lightLevelMin, int lightLevelMax) {
        this.growthReq = growthReq;
        this.difficulty = difficulty;
        this.lightLevelMin = lightLevelMin;
        this.lightLevelMax = lightLevelMax;
    }
}
