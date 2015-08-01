package tehnut.resourceful.crops.api.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * A set {@link Block} and meta to check against.
 */
public class BlockStack {

    private IBlockState state;

    public BlockStack(@Nullable IBlockState state) {
        this.state = state;
    }

    public Block getBlock() {
        return state.getBlock();
    }

    public IBlockState getBlockState() {
        return state;
    }

    public int getMeta() {
        return getBlock().getMetaFromState(state);
    }

    public String getDisplayName() {
        return toItemStack().getDisplayName();
    }

    public ItemStack toItemStack() {
        return toItemStack(1);
    }

    public ItemStack toItemStack(int amount) {
        return new ItemStack(getBlock(), amount, getBlock().getMetaFromState(getBlockState()));
    }
}
