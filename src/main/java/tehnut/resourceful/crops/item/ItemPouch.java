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

@ModItem(name = "ItemPouch")
public class ItemPouch extends Item implements IPlantable {

    public ItemPouch() {
        super();

        setUnlocalizedName(ModInformation.ID + ".pouch");
        setCreativeTab(ResourcefulCrops.tabResourcefulCrops);
        setHasSubtypes(true);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {

        boolean success = false;

        for (int posX = pos.getX() - 1; posX <= pos.getX() + 1; posX++) {
            for (int posZ = pos.getZ() - 1; posZ <= pos.getZ() + 1; posZ++) {

                Block placed = world.getBlockState(new BlockPos(posX, pos.getY(), posZ)).getBlock();

                if (placed.canSustainPlant(world, new BlockPos(posX, pos.getY(), posZ), EnumFacing.UP, this) && side == EnumFacing.UP && Utils.isValidSeed(Utils.getItemDamage(stack)) && world.isAirBlock(new BlockPos(posX, pos.getY() + 1, posZ))) {
                    world.setBlockState(new BlockPos(posX, pos.getY() + 1, posZ), BlockRegistry.getBlock(BlockRCrop.class).getDefaultState());
                    ((TileRCrop) world.getTileEntity(new BlockPos(posX, pos.getY() + 1, posZ))).setSeedName(SeedRegistry.getSeed(Utils.getItemDamage(stack)).getName());
                    if (!player.capabilities.isCreativeMode)
                        player.inventory.decrStackSize(player.inventory.currentItem, 1);

                    success = true;
                }
            }
        }

        return success;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List<ItemStack> list) {
        for (int i = 0; i < SeedRegistry.getSize(); i++)
            if (Utils.isValidSeed(i))
                list.add(new ItemStack(this, 1, i));

        if (SeedRegistry.isEmpty())
            list.add(Utils.getInvalidSeed(this));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (Utils.isValidSeed(Utils.getItemDamage(stack)))
            return String.format(StatCollector.translateToLocal(getUnlocalizedName()), StatCollector.translateToLocal(SeedRegistry.getSeed(Utils.getItemDamage(stack)).getName()));
        else
            return String.format(StatCollector.translateToLocal(getUnlocalizedName()), StatCollector.translateToLocal("info.ResourcefulCrops.torn"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
        if (!Utils.isValidSeed(Utils.getItemDamage(stack)))
            list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("info.ResourcefulCrops.warn"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if (pass == 1 && Utils.isValidSeed(Utils.getItemDamage(stack)))
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
