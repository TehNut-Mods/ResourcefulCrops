package tehnut.resourceful.crops.api.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Chance {

    private double extraSeed;
    private double essenceDrop;

    protected Chance(double extraSeed, double essenceDrop) {
        this.extraSeed = extraSeed;
        this.essenceDrop = essenceDrop;
    }
}
