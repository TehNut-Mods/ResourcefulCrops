package tehnut.resourceful.crops.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemBlockMulti extends ItemBlock {

    private final String[] names;

    public ItemBlockMulti(Block block, String... names) {
        super(block);

        setHasSubtypes(true);

        this.names = names;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (!isInCreativeTab(tab))
            return;

        for (int i = 0; i < names.length; i++)
            subItems.add(new ItemStack(this, 1, i));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + names[stack.getItemDamage() % names.length];
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
