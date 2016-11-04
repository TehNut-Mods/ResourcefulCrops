package tehnut.resourceful.crops.api.base;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import tehnut.resourceful.crops.ConfigHandler;

@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ChanceBuilder {

    private double extraSeed = ConfigHandler.defaultExtraSeedChance;
    private double essenceDrop = ConfigHandler.defaultEssenceDropChance;

    public Chance build() {
        return new Chance(extraSeed, essenceDrop);
    }
}
