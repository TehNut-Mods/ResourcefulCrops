package tehnut.resourceful.crops.item;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.compat.CompatibilitySeed;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.util.Utils;

import java.util.List;

public class ItemShard extends Item {

    public ItemShard() {
        super();

        setUnlocalizedName(ModInformation.ID + ".shard");
        setTextureName(ModInformation.TEXLOC + "shard_base");
        setCreativeTab(ResourcefulCrops.tabResourcefulCrops);
        setHasSubtypes(true);
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        for (Seed seed : SeedRegistry.getSeedList())
            list.add(new ItemStack(this, 1, SeedRegistry.getIndexOf(seed)));

        if (SeedRegistry.isEmpty())
            list.add(Utils.getInvalidSeed(this));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

        for (CompatibilitySeed compatSeed : CompatibilitySeed.values())
            if (Loader.isModLoaded(compatSeed.getModid()) && compatSeed.getConfig())
                stack = compatSeed.onRightClick(stack, world, player);

        return stack;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (Utils.isValidSeed(Utils.getItemDamage(stack)))
            return String.format(StatCollector.translateToLocal(getUnlocalizedName()), StatCollector.translateToLocal(SeedRegistry.getSeed(Utils.getItemDamage(stack)).getName()));
        else
            return String.format(StatCollector.translateToLocal(getUnlocalizedName()), StatCollector.translateToLocal("info.ResourcefulCrops.broken"));
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        if (!Utils.isValidSeed(Utils.getItemDamage(stack)))
            list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("info.ResourcefulCrops.warn"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if (pass == 1 && Utils.isValidSeed(stack.getItemDamage()))
            return SeedRegistry.getSeed(Utils.getItemDamage(stack)).getColor().getRGB();
        else
            return super.getColorFromItemStack(stack, pass);
    }

    @Override
    public int getRenderPasses(int metadata) {
        return requiresMultipleRenderPasses() ? 2 : 1;
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
