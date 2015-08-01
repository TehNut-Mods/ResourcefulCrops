package tehnut.resourceful.crops.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.*;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.ModInformation;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.base.Seed;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.crops.registry.SeedRegistry;
import tehnut.resourceful.crops.tile.TileRCrop;
import tehnut.resourceful.crops.util.BlockStack;
import tehnut.resourceful.crops.util.Utils;
import tehnut.resourceful.crops.util.helper.LogHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockRCrop extends BlockCrops implements ITileEntityProvider {

    private static boolean shouldDrop = true;

    public BlockRCrop() {
        super();

        setUnlocalizedName(ModInformation.ID + ".crop");
    }

    public static int getTileSeedIndex(World world, BlockPos pos) {
        TileEntity crop = world.getTileEntity(pos);
        int seedIndex = Utils.getInvalidSeed(ItemRegistry.seed).getItemDamage();

        if (crop != null && crop instanceof TileRCrop) {
            String seedName = ((TileRCrop) crop).getSeedName();
            seedIndex = SeedRegistry.getIndexOf(seedName);
        }

        return seedIndex;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {

        int seedIndex = getTileSeedIndex(world, pos);
        Seed seed = SeedRegistry.getSeed(seedIndex);

        if (seedIndex == Short.MAX_VALUE)
            return;

        BlockPos reqPos = new BlockPos(pos.getX(), pos.getY() - 2, pos.getZ());
        BlockStack blockReq = new BlockStack(world.getBlockState(reqPos));

        func_176475_e(world, pos, state);

        if (seed.getSeedReq().getGrowthReq() == null || seed.getSeedReq().getGrowthReq().equals(blockReq)) {

            int lightLevel = world.getLight(pos.offsetUp());

            if (lightLevel >= seed.getSeedReq().getLightLevelMin() && lightLevel <= seed.getSeedReq().getLightLevelMax()) {
                int meta = this.getMetaFromState(world.getBlockState(pos));

                if (meta < 7) {
                    float growthChance = getGrowthChance(this, world, pos);

                    if (random.nextInt((int)(25.0F / growthChance) + 1) == 0) {
                        ++meta;
                        world.setBlockState(pos, this.getStateFromMeta(meta), 2);
                    }
                }
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity cropTile = world.getTileEntity(pos);

        if (Utils.isValidSeed(((TileRCrop)cropTile).getSeedName())) {
            if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemHoe) {
                Seed seed = SeedRegistry.getSeed(((TileRCrop) cropTile).getSeedName());

                ResourcefulCrops.proxy.addChatMessage(String.format(StatCollector.translateToLocal("chat.ResourcefulCrops.req.growth"), seed.getSeedReq().getGrowthReq() != null ? seed.getSeedReq().getGrowthReq().getDisplayName() : StatCollector.translateToLocal("info.ResourcefulCrops.anything")), 1);

                if (seed.getSeedReq().getLightLevelMax() == Integer.MAX_VALUE)
                    ResourcefulCrops.proxy.addChatMessage(String.format(StatCollector.translateToLocal("chat.ResourcefulCrops.req.light.above"), seed.getSeedReq().getLightLevelMin()), 2);
                else if (seed.getSeedReq().getLightLevelMin() == 0)
                    ResourcefulCrops.proxy.addChatMessage(String.format(StatCollector.translateToLocal("chat.ResourcefulCrops.req.light.below"), seed.getSeedReq().getLightLevelMax()), 2);
                else
                    ResourcefulCrops.proxy.addChatMessage(String.format(StatCollector.translateToLocal("chat.ResourcefulCrops.req.light.between"), seed.getSeedReq().getLightLevelMin(), seed.getSeedReq().getLightLevelMax()), 2);

                return true;
            }

            if (!player.isSneaking() || player.getHeldItem() == null && ConfigHandler.enableRightClickHarvest) {
                if (world.getBlockState(pos) == BlockRegistry.crop.getDefaultState() && this.getMetaFromState(world.getBlockState(pos)) >= 7) {
                    if (!world.isRemote) {
                        world.setBlockState(pos, this.getDefaultState(), 3);
                        world.spawnEntityInWorld(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemRegistry.shard, 1, getTileSeedIndex(world, pos))));
                    }
                    player.swingItem();
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        dropItems(world, pos, state);

        super.breakBlock(world, pos, state);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune) {
        return null;
    }

    @Override
    public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return new ArrayList<ItemStack>();
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ItemRegistry.seed, 1, getTileSeedIndex(world, pos));
    }

    public void dropItems(World world, BlockPos pos, IBlockState state) {
        if (shouldDrop)
            for (ItemStack stack : getDrops(world, pos, state))
                world.spawnEntityInWorld(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack));
    }

    public List<ItemStack> getDrops(World world, BlockPos pos, IBlockState state) {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        int seedIndex = getTileSeedIndex(world, pos);

        if (this.getMetaFromState(state) <= 6) {
            drops.add(new ItemStack(ItemRegistry.seed, 1, seedIndex));
        } else {
            drops.add(new ItemStack(ItemRegistry.seed, 1, seedIndex));
            drops.add(new ItemStack(ItemRegistry.shard, 1, seedIndex));
        }

        return drops;
    }

    public static void setShouldDrop(boolean drop) {
        shouldDrop = drop;
    }

    // IGrowable

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    // ITileEntityProvider

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileRCrop();
    }
}
