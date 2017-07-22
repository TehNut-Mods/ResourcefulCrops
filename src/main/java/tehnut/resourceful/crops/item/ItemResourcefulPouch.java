package tehnut.resourceful.crops.item;

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
import tehnut.resourceful.crops.block.tile.TileSeedContainer;
import tehnut.resourceful.crops.core.RegistrarResourcefulCrops;
import tehnut.resourceful.crops.core.data.Seed;
import tehnut.resourceful.crops.util.Util;

public class ItemResourcefulPouch extends ItemResourceful implements IPlantable {

    public ItemResourcefulPouch() {
        super("pouch");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        Seed seed = getSeed(stack);
        if (facing != EnumFacing.UP || seed == null)
            return EnumActionResult.PASS;

        boolean flag = false;
        BlockPos.MutableBlockPos settablePos = new BlockPos.MutableBlockPos(pos);
        for (int x = pos.getX() - 1; x < pos.getX() + 2; x++) {
            for (int z = pos.getZ() - 1; z < pos.getZ() + 2; z++) {
                settablePos.setPos(x, pos.getY(), z);
                IBlockState worldState = world.getBlockState(settablePos);
                if (player.canPlayerEdit(settablePos.up(), facing, stack) && worldState.getBlock().canSustainPlant(worldState, world, settablePos, EnumFacing.UP, this) && world.isAirBlock(settablePos.up())) {
                    world.setBlockState(settablePos.up(), RegistrarResourcefulCrops.CROP.getDefaultState());
                    if (Util.getTile(TileSeedContainer.class, world, settablePos.up()) == null)
                        world.setTileEntity(settablePos.up(), new TileSeedContainer(seed.getRegistryName()));
                    else
                        Util.getTile(TileSeedContainer.class, world, settablePos.up()).setSeedKey(seed.getRegistryName());
                    flag = true;
                }
            }
        }

        if (flag) {
            if (!player.capabilities.isCreativeMode)
                player.inventory.decrStackSize(player.inventory.currentItem, 1);
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.FAIL;
    }

    // IPlantable

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return RegistrarResourcefulCrops.CROP.getDefaultState();
    }
}
