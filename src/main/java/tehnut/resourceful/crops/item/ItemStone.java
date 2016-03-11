package tehnut.resourceful.crops.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.repack.tehnut.lib.annot.ModItem;

import java.util.List;

@ModItem(name = "ItemStone")
public class ItemStone extends Item {

    String[] stones = {"mundane", "magical", "infused", "arcane", "true"};

    public ItemStone() {
        super();

        setUnlocalizedName(ModInformation.ID + ".stone");
        setCreativeTab(ResourcefulCrops.tabResourcefulCrops);
        setMaxStackSize(1);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName() + "." + stones[stack.getItemDamage() % stones.length];
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List<ItemStack> list) {
        for (int i = 0; i < stones.length; i++)
            list.add(new ItemStack(this, 1, i));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return stack.getItemDamage() == 4;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 0:
                return EnumRarity.COMMON;
            case 1:
                return EnumRarity.COMMON;
            case 2:
                return EnumRarity.UNCOMMON;
            case 3:
                return EnumRarity.RARE;
            case 4:
                return EnumRarity.EPIC;
            default:
                return EnumRarity.COMMON;
        }
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        return stack;
    }
}
