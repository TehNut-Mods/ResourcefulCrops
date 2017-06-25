package tehnut.resourceful.crops.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.resourceful.crops.ResourcefulCrops;

public class ItemEarthStone extends Item {

    public static final String[] STONES = { "mundane", "magical", "infused", "arcane", "true" };

    public ItemEarthStone() {
        super();

        setCreativeTab(ResourcefulCrops.TAB_RCROP);
        setUnlocalizedName(ResourcefulCrops.MODID + ".stone");
        setHasSubtypes(true);
        setMaxStackSize(1);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName() + "." + STONES[stack.getItemDamage() % STONES.length];
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (!isInCreativeTab(tab))
            return;

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
