package tehnut.resourceful.crops.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.resourceful.crops.ResourcefulCrops;

public class ItemEssence extends Item {

    public static final String[] NAMES = new String[] { "", ".mundane", ".magical", ".infused", ".arcane" };

    public ItemEssence() {
        super();

        setUnlocalizedName(ResourcefulCrops.MODID + ".essence");
        setCreativeTab(ResourcefulCrops.TAB_RCROP);
        setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for (int i = 0; i < NAMES.length; i++)
            subItems.add(new ItemStack(item, 1, i));
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
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + NAMES[stack.getItemDamage() % NAMES.length];
    }
}
