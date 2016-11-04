package tehnut.resourceful.crops2.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockMulti extends ItemBlock {

    private final String[] names;

    public ItemBlockMulti(Block block, String... names) {
        super(block);

        setHasSubtypes(true);

        this.names = names;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems) {
        for (int i = 0; i < names.length; i++)
            subItems.add(new ItemStack(item, 1, i));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + names[stack.getItemDamage()];
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
