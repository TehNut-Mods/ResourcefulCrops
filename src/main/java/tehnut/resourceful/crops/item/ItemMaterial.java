package tehnut.resourceful.crops.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import tehnut.lib.annot.ModItem;
import tehnut.lib.iface.IVariantProvider;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.api.ResourcefulAPI;

import java.util.ArrayList;
import java.util.List;

@ModItem(name = ResourcefulAPI.MATERIAL)
public class ItemMaterial extends Item implements IVariantProvider {

    String[] materials = {"", ".mundane", ".magical", ".infused", ".arcane"};

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

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List<ItemStack> list) {
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

    @Override
    public List<Pair<Integer, String>> getVariants() {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        ret.add(new ImmutablePair<Integer, String>(0, "type=normal"));
        ret.add(new ImmutablePair<Integer, String>(1, "type=mundane"));
        ret.add(new ImmutablePair<Integer, String>(2, "type=magical"));
        ret.add(new ImmutablePair<Integer, String>(3, "type=infused"));
        ret.add(new ImmutablePair<Integer, String>(4, "type=arcane"));
        return ret;
    }
}
