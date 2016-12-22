package tehnut.resourceful.crops2.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.resourceful.crops2.ResourcefulCrops2;
import tehnut.resourceful.crops2.core.data.Seed;
import tehnut.resourceful.crops2.core.ModObjects;
import tehnut.resourceful.crops2.core.data.SeedStack;
import tehnut.resourceful.crops2.util.Util;

import java.util.List;

public class ItemResourceful extends Item {

    private final String base;

    public ItemResourceful(String base) {
        setUnlocalizedName(ResourcefulCrops2.MODID + "." + base);
        setCreativeTab(ResourcefulCrops2.TAB_RCROP);
        setHasSubtypes(true);

        this.base = base;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (Seed seed : ModObjects.SEEDS.getValues())
            subItems.add(getResourcefulStack(itemIn, seed.getRegistryName()));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        Seed seed = getSeed(stack);
        if (seed == null) {
            tooltip.add(TextFormatting.RED + net.minecraft.client.resources.I18n.format("info.resourcefulcrops.invalid"));
            return;
        }

        tooltip.add(net.minecraft.client.resources.I18n.format("info.resourcefulcrops.tier", seed.getTier() + 1));
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        Seed seed = getSeed(stack);
        if (seed != null) {
            String unlocFormat = "item.resourcefulcrops." + base + ".name";
            String seedUnloc = "seed.resourcefulcrops." + Util.cleanString(seed.getName()) + ".name";
            String seedName = Util.prettifyString(seed.getName());
            if (I18n.canTranslate(seedUnloc))
                seedName = I18n.translateToLocal(seedUnloc);

            return I18n.translateToLocalFormatted(unlocFormat, seedName);
        }

        return super.getItemStackDisplayName(stack);
    }

    public Seed getSeed(ItemStack stack) {
        if (stack == null || stack.getItemDamage() == Short.MAX_VALUE - 1)
            return null;

        if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("seed"))
            return null;

        return ModObjects.SEEDS.getValue(new ResourceLocation(stack.getTagCompound().getString("seed")));
    }

    public static ItemStack getResourcefulStack(Item item, ResourceLocation key) {
        return getResourcefulStack(item, key, 1);
    }

    public static ItemStack getResourcefulStack(Item item, ResourceLocation key, int amount) {
        return getResourcefulStack(new SeedStack((ItemResourceful) item, key, amount));
    }

    public static ItemStack getResourcefulStack(SeedStack seedStack) {
        ItemStack stack = new ItemStack(seedStack.getType(), seedStack.getAmount(), 0);
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setString("seed", seedStack.getSeed().getRegistryName().toString());
        return stack;
    }
}
