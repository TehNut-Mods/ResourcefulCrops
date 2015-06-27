package tehnut.resourceful.crops.base;

import tehnut.resourceful.crops.util.BlockStack;

/**
 * Factory for creating SeedReqs.
 * Documentation for each field can be found in {@link SeedReq}
 */
public class SeedReqBuilder {

    private BlockStack growthReq = null;

    public SeedReqBuilder() {

    }

    public SeedReqBuilder setGrowthReq(BlockStack growthReq) {
        this.growthReq = growthReq;
        return this;
    }

    public SeedReq build() {
        return new SeedReq(growthReq);
    }
}
