package tehnut.resourceful.crops.api.base;

public class Chance {

    private double extraSeed;
    private double essenceDrop;

    protected Chance(double extraSeed, double essenceDrop) {
        this.extraSeed = extraSeed;
        this.essenceDrop = essenceDrop;
    }

    public double getExtraSeed() {
        return extraSeed;
    }

    public double getEssenceDrop() {
        return essenceDrop;
    }
}
