package tehnut.resourceful.crops.api.base;

import tehnut.resourceful.crops.api.util.BlockStack;

public class SeedReq {

    private BlockStack growthReq;
    private int lightLevelMin;
    private int lightLevelMax;

    /**
     * To create a seed requirement, use {@link SeedReqBuilder}
     *
     * @param growthReq     - BlockStack needed under the soil for the crop to grow.
     * @param lightLevelMin - Minimum light level required for a crop to grow.
     * @param lightLevelMax - Maximum light level the crop can grow at.
     */
    protected SeedReq(BlockStack growthReq, int lightLevelMin, int lightLevelMax) {
        this.growthReq = growthReq;
        this.lightLevelMin = lightLevelMin;
        this.lightLevelMax = lightLevelMax;
    }

    public BlockStack getGrowthReq() {
        return growthReq;
    }

    public int getLightLevelMin() {
        return lightLevelMin;
    }

    public int getLightLevelMax() {
        return lightLevelMax;
    }

    @Override
    public String toString() {
        return "SeedReq{" +
                "growthReq=" + growthReq +
                ", lightLevelMin=" + lightLevelMin +
                ", lightLevelMax=" + lightLevelMax +
                '}';
    }
}
