package tehnut.resourceful.crops.core.data;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class InfoOverride {

    public static final InfoOverride DEFAULT = new InfoOverride(null, null, null, null, null);

    @Nullable
    private final String langKey; // Base lang key for the given seed. appended with with the type (ie: shard)
    @Nullable
    private final StateInfo blockState; // Custom location to find the block state.
    @Nullable
    private final ModelInfo seed; // Custom location to find the seed model.
    @Nullable
    private final ModelInfo shard; // Custom location to find the shard model.
    @Nullable
    private final ModelInfo pouch; // Custom location to find the pouch model.

    public InfoOverride(@Nullable String langKey, @Nullable StateInfo blockState, @Nullable ModelInfo seed, @Nullable ModelInfo shard, @Nullable ModelInfo pouch) {
        this.langKey = langKey;
        this.blockState = blockState;
        this.seed = seed;
        this.shard = shard;
        this.pouch = pouch;
    }

    @Nullable
    public String getLangKey() {
        return langKey;
    }

    @Nullable
    public StateInfo getBlockState() {
        return blockState;
    }

    @Nullable
    public ModelInfo getModel(String type) {
        switch (type) {
            case "seed": return seed;
            case "shard": return shard;
            case "pouch": return pouch;
            default: return null;
        }
    }

    public static class StateInfo {

        public static final StateInfo DEFAULT = new StateInfo("resourcefulcrops:crop", true);

        @Nonnull
        private String path = "resourcefulcrops:crop";
        private boolean shouldColor = true;

        public StateInfo(@Nonnull String path, boolean shouldColor) {
            this.path = path;
            this.shouldColor = shouldColor;
        }

        public StateInfo(@Nonnull ResourceLocation path, boolean shouldColor) {
            this(path.toString(), shouldColor);
        }

        @Nonnull
        public ResourceLocation getPath() {
            return new ResourceLocation(path);
        }

        public boolean shouldColor() {
            return shouldColor;
        }
    }

    public static class ModelInfo {

        public static final ModelInfo DEFAULT = new ModelInfo("missingno", "inventory", true);

        @Nonnull
        private String path = "resourcefulcrops:seed";
        @Nonnull
        private String variant = "inventory";
        private boolean shouldColor = true;

        public ModelInfo(@Nonnull String path, @Nonnull String variant, boolean shouldColor) {
            this.path = path;
            this.variant = variant;
            this.shouldColor = shouldColor;
        }

        public ModelInfo(@Nonnull ResourceLocation path, @Nonnull String variant, boolean shouldColor) {
            this(path.toString(), variant, shouldColor);
        }

        @Nonnull
        public ResourceLocation getPath() {
            return new ResourceLocation(path);
        }

        @Nonnull
        public String getVariant() {
            return variant;
        }

        public boolean shouldColor() {
            return shouldColor;
        }
    }
}
