package tehnut.resourceful.crops.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tehnut.resourceful.crops.ModInformation;
import tehnut.resourceful.crops.ResourcefulCrops;

import java.util.List;

public class ItemMaterial extends Item {

    String[] materials = { "", ".mundane", ".magical", ".infused", ".arcane" };

    public ItemMaterial() {
        super();

        setUnlocalizedName(ModInformation.ID + ".dust");
        setCreativeTab(ResourcefulCrops.tabResourcefulCrops);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName() + materials[stack.getItemDamage() % materials.length];
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        for (int i = 0; i < materials.length; i++)
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
}
