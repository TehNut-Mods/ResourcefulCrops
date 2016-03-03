package tehnut.resourceful.crops.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.crops.tile.TileRCrop;
import tehnut.resourceful.crops.util.Utils;
import tehnut.resourceful.repack.tehnut.lib.annot.ModItem;

import java.util.List;

@ModItem(name = "ItemSeed")
public class ItemSeed extends Item implements IPlantable {

    public ItemSeed() {
        super();

        setUnlocalizedName(ModInformation.ID + ".seed");
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

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {

        Block placed = world.getBlockState(pos).getBlock();

        if (placed.canSustainPlant(world, pos, EnumFacing.UP, this) && side == EnumFacing.UP && Utils.isValidSeed(Utils.getItemDamage(stack)) && world.isAirBlock(pos.offset(EnumFacing.UP))) {
            world.setBlockState(pos.offset(EnumFacing.UP), BlockRegistry.getBlock(BlockRCrop.class).getDefaultState());
            ((TileRCrop) world.getTileEntity(pos.offset(EnumFacing.UP))).setSeedName(SeedRegistry.getSeed(Utils.getItemDamage(stack)).getName());
            if (!player.capabilities.isCreativeMode)
                player.inventory.decrStackSize(player.inventory.currentItem, 1);

            return true;
        }

        return false;
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
        if (pass == 0 && Utils.isValidSeed(Utils.getItemDamage(stack)))
            return SeedRegistry.getSeed(Utils.getItemDamage(stack)).getColor().getRGB();
        else
            return super.getColorFromItemStack(stack, pass);
    }

    // IPlantable

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return BlockRegistry.getBlock(BlockRCrop.class).getDefaultState();
    }
}
