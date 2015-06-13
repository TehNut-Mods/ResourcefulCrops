package tehnut.resourceful.crops.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockROre extends ItemBlock {

    public static final String[] names = {"", ".nether"};

    public ItemBlockROre(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName() + "" + names[stack.getItemDamage() % names.length];
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
