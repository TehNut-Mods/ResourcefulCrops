package tehnut.resourceful.crops.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.tile.TileRCrop;
import tehnut.resourceful.crops.util.Utils;

import java.util.List;

public class ItemSeed extends Item implements IPlantable {

    public ItemSeed() {
        super();

        setUnlocalizedName(ModInformation.ID + ".seed");
        setTextureName(ModInformation.TEXLOC + "seed_base");
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

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        Block placed = world.getBlock(x, y, z);
        Seed seed = SeedRegistry.getSeed(Utils.getItemDamage(stack));

        if (isSoil(world, x, y, z, placed, seed)&& ForgeDirection.getOrientation(side) == ForgeDirection.UP && Utils.isValidSeed(Utils.getItemDamage(stack)) && world.isAirBlock(x, y + 1, z)) {
            world.setBlock(x, y + 1, z, BlockRegistry.crop);
            ((TileRCrop) world.getTileEntity(x, y + 1, z)).setSeedName(seed.getName());
            if (!player.capabilities.isCreativeMode)
                player.inventory.decrStackSize(player.inventory.currentItem, 1);

            return true;
        }

        return false;
    }

    public boolean isSoil(World world, int x, int y, int z, Block block, Seed seed) {
        return (!seed.getNether() && block.canSustainPlant(world, x, y, z, ForgeDirection.UP, this)) || (seed.getNether() && world.getBlock(x, y, z) == Blocks.soul_sand);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (Utils.isValidSeed(Utils.getItemDamage(stack)))
            return String.format(StatCollector.translateToLocal(getUnlocalizedName()), StatCollector.translateToLocal(SeedRegistry.getSeed(Utils.getItemDamage(stack)).getName()));
        else
            return String.format(StatCollector.translateToLocal(getUnlocalizedName()), StatCollector.translateToLocal("info.ResourcefulCrops.dead"));
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        if (!Utils.isValidSeed(Utils.getItemDamage(stack)))
            list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("info.ResourcefulCrops.warn"));
        else
            list.add(String.format(StatCollector.translateToLocal("info.ResourcefulCrops.tier"), SeedRegistry.getSeed(Utils.getItemDamage(stack)).getTier()));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if (pass == 1 && Utils.isValidSeed(Utils.getItemDamage(stack)))
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

    // IPlantable

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Crop;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return BlockRegistry.crop;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return 0;
    }
}
