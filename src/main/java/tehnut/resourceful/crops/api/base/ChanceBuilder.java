package tehnut.resourceful.crops.api.base;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ChanceBuilder {

    private double extraSeed = 0.0;
    private double essenceDrop = 0.0;

    public Chance build() {
        return new Chance(extraSeed, essenceDrop);
    }
}
