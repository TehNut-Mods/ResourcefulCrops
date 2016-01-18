package tehnut.resourceful.crops.api.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameData;

/**
 * A set {@link Block} and meta to check against.
 */
public class BlockStack {

    private final Block block;
    private final int meta;
    private final IBlockState state;

    public BlockStack(Block block, int meta) {
        this.block = block;
        this.meta = meta;
        this.state = block.getStateFromMeta(meta);
    }

    public BlockStack(Block block) {
        this(block, 0);
    }

    public Block getBlock() {
        return block;
    }

    public int getMeta() {
        return meta;
    }

    public IBlockState getState() {
        return state;
    }

    public ItemStack toStack(int amount) {
        return new ItemStack(getBlock(), amount, getMeta());
    }

    public ItemStack toStack() {
        return toStack(1);
    }

    public String getDisplayName() {
        return toStack().getDisplayName();
    }

    @Override
    public String toString() {
        return GameData.getBlockRegistry().getNameForObject(getBlock()) + ":" + getMeta();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlockStack that = (BlockStack) o;

        return getMeta() == that.getMeta() && (getBlock() != null ? getBlock().equals(that.getBlock()) : that.getBlock() == null);
    }

    @Override
    public int hashCode() {
        int result = getBlock() != null ? getBlock().hashCode() : 0;
        result = 31 * result + getMeta();
        return result;
    }

    public static BlockStack getStackFromPos(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return new BlockStack(state.getBlock(), state.getBlock().getMetaFromState(state));
    }
}
