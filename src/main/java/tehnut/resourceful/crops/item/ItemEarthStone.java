package tehnut.resourceful.crops.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.resourceful.crops.ResourcefulCrops2;

public class ItemEarthStone extends Item {

    public static final String[] STONES = { "mundane", "magical", "infused", "arcane", "true" };

    public ItemEarthStone() {
        super();

        setCreativeTab(ResourcefulCrops2.TAB_RCROP);
        setUnlocalizedName(ResourcefulCrops2.MODID + ".stone");
        setHasSubtypes(true);
        setMaxStackSize(1);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName() + "." + STONES[stack.getItemDamage() % STONES.length];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for (int i = 0; i < STONES.length; i++)
            subItems.add(new ItemStack(this, 1, i));
    }

    @SideOnly(Side.CLIENT)
    @Override
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
    public ItemStack getContainerItem(ItemStack stack) {
        return stack.copy();
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }
}
