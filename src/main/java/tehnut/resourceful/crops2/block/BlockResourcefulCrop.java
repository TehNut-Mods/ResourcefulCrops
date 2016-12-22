package tehnut.resourceful.crops2.block;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import tehnut.resourceful.crops2.ResourcefulCrops2;
import tehnut.resourceful.crops2.block.tile.TileSeedContainer;
import tehnut.resourceful.crops2.core.ModObjects;
import tehnut.resourceful.crops2.core.data.Seed;
import tehnut.resourceful.crops2.item.ItemResourceful;
import tehnut.resourceful.crops2.util.Util;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockResourcefulCrop extends BlockCrops {

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (canGrow(world, pos, state))
            super.updateTick(world, pos, state, rand);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> drops = new ArrayList<ItemStack>();
            drops.add(getFoodStack(getSeed(), world, pos));
            if (getAge(state) >= 7)
                drops.add(getFoodStack(getCrop(), world, pos));
        return drops;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tile, @Nullable ItemStack stack) {
        super.harvestBlock(world, player, pos, state, tile, stack);
        world.setBlockToAir(pos);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return getItem(world, pos, state);
    }

    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return getFoodStack(getSeed(), world, pos);
    }

    @Override
    protected Item getSeed() {
        return ModObjects.SEED;
    }

    @Override
    protected Item getCrop() {
        return ModObjects.SHARD;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return ResourcefulCrops2.DEV_MODE;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileSeedContainer();
    }

    public boolean canGrow(World world, BlockPos pos, IBlockState state) {
        if (isMaxAge(state))
            return false;

        TileSeedContainer seedContainer = Util.getSeedContainer(world, pos);
        if (seedContainer == null)
            return false;

        Seed seed = ModObjects.SEEDS.getValue(seedContainer.getSeedKey());
        if (seed == null)
            return false;

        if (world.getLightFromNeighbors(pos.up()) < seed.getGrowthRequirement().getMinLight() && world.getLightFromNeighbors(pos.up()) > seed.getGrowthRequirement().getMaxLight())
            return false;

        if (seed.getGrowthRequirement().getRequiredState() != null && !world.getBlockState(pos.down(2)).equals(seed.getGrowthRequirement().getRequiredState()))
            return false;

        return true;
    }

    private ItemStack getFoodStack(Item toDrop, IBlockAccess world, BlockPos pos) {
        TileSeedContainer cropTile = Util.getSeedContainer(world, pos);
        if (cropTile != null)
            return ItemResourceful.getResourcefulStack(toDrop, cropTile.getSeedKey());

        return new ItemStack(toDrop, 1, Short.MAX_VALUE - 1);
    }
}
