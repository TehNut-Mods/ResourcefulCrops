package tehnut.resourceful.crops.core.data;

import net.minecraft.block.state.IBlockState;

import javax.annotation.Nullable;

public final class GrowthRequirement {

    public static final GrowthRequirement DEFAULT = new GrowthRequirement(7, 15, null);

    private final int minLight;
    private final int maxLight;
    @Nullable
    private final IBlockState requiredState;

    public GrowthRequirement(int minLight, int maxLight, @Nullable IBlockState requiredState) {
        this.minLight = minLight;
        this.maxLight = maxLight;
        this.requiredState = requiredState;
    }

    public int getMinLight() {
        return minLight;
    }

    public int getMaxLight() {
        return maxLight;
    }

    @Nullable
    public IBlockState getRequiredState() {
        return requiredState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrowthRequirement)) return false;

        GrowthRequirement that = (GrowthRequirement) o;

        if (getMinLight() != that.getMinLight()) return false;
        return getMaxLight() == that.getMaxLight();

    }

    @Override
    public int hashCode() {
        int result = getMinLight();
        result = 31 * result + getMaxLight();
        return result;
    }
}
