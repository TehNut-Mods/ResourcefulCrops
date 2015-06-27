package tehnut.resourceful.crops.base;

import tehnut.resourceful.crops.util.BlockStack;

public class SeedReq {

    private BlockStack growthReq;

    /**
     * To create a seed requirement, use {@link SeedReqBuilder}
     *
     * @param growthReq - BlockStack needed under the soil for the crop to grow.
     */
    protected SeedReq(BlockStack growthReq) {
        this.growthReq = growthReq;
    }

    public BlockStack getGrowthReq() {
        return growthReq;
    }

    @Override
    public String toString() {
        return "SeedReq{" +
                "growthReq=" + growthReq +
                '}';
    }
}
