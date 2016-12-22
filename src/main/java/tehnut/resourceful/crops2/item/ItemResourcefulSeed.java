package tehnut.resourceful.crops2.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import tehnut.resourceful.crops2.block.tile.TileSeedContainer;
import tehnut.resourceful.crops2.core.data.Seed;
import tehnut.resourceful.crops2.core.ModObjects;
import tehnut.resourceful.crops2.util.Util;

public class ItemResourcefulSeed extends ItemResourceful implements IPlantable {

    public ItemResourcefulSeed() {
        super("seed");
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Seed seed = getSeed(stack);
        IBlockState worldState = world.getBlockState(pos);
        if (seed != null && facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, stack) && worldState.getBlock().canSustainPlant(worldState, world, pos, EnumFacing.UP, this) && world.isAirBlock(pos.up())) {
            world.setBlockState(pos.up(), ModObjects.CROP.getDefaultState());
            if (Util.getSeedContainer(world, pos.up()) == null)
                world.setTileEntity(pos.up(), new TileSeedContainer(seed.getRegistryName()));
            else
                ((TileSeedContainer) world.getTileEntity(pos.up())).setSeedKey(seed.getRegistryName());
            if (!player.capabilities.isCreativeMode)
                player.inventory.decrStackSize(player.inventory.currentItem, 1);
            return EnumActionResult.SUCCESS;
        } else return EnumActionResult.FAIL;
    }

    // IPlantable

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return ModObjects.CROP.getDefaultState();
    }
}
