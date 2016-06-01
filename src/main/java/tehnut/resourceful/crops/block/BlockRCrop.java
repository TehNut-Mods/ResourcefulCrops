package tehnut.resourceful.crops.block;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tehnut.lib.annot.Handler;
import tehnut.lib.annot.ModBlock;
import tehnut.lib.annot.Used;
import tehnut.lib.util.helper.BlockHelper;
import tehnut.lib.util.helper.ItemHelper;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.achievement.AchievementTrigger;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.util.BlockStack;
import tehnut.resourceful.crops.item.ItemMaterial;
import tehnut.resourceful.crops.item.ItemSeed;
import tehnut.resourceful.crops.item.ItemShard;
import tehnut.resourceful.crops.registry.AchievementRegistry;
import tehnut.resourceful.crops.tile.TileRCrop;
import tehnut.resourceful.crops.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ModBlock(name = ResourcefulAPI.CROP, tileEntity = TileRCrop.class)
@Handler
public class BlockRCrop extends BlockCrops {

    public BlockRCrop() {
        super();

        setUnlocalizedName(ModInformation.ID + ".crop");
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {

        int seedIndex = getTileSeedIndex(world, pos);
        Seed seed = ResourcefulAPI.SEEDS.getRaw(seedIndex);

        if (seedIndex == Short.MAX_VALUE || seed == null)
            return;

        BlockPos reqPos = new BlockPos(pos.getX(), pos.getY() - 2, pos.getZ());
        BlockStack blockReq = BlockStack.getStackFromPos(world, reqPos);

        checkAndDropBlock(world, pos, state);

        if (seed.getRequirement().getGrowthReq() == null || seed.getRequirement().getGrowthReq().equals(blockReq)) {

            int lightLevel = world.getLight(pos.up());

            if (lightLevel >= seed.getRequirement().getLightLevelMin() && lightLevel <= seed.getRequirement().getLightLevelMax()) {
                int meta = this.getMetaFromState(world.getBlockState(pos));

                if (meta < 7) {
                    float growthChance = getGrowthChance(this, world, pos);

                    if (random.nextInt((int) (25.0F / growthChance) + 1) == 0) {
                        ++meta;
                        world.setBlockState(pos, this.getStateFromMeta(meta), 2);
                    }
                }
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity cropTile = world.getTileEntity(pos);

        if (hand != EnumHand.MAIN_HAND)
            return false;

        if (Utils.isValidSeed(((TileRCrop) cropTile).getSeedName().toString())) {
            if (player.getHeldItem(hand) != null && player.getHeldItem(hand).getItem() instanceof ItemHoe) {
                AchievementTrigger.triggerAchievement(player, AchievementRegistry.getInfo);
                return doReqInfo(player, ((TileRCrop) cropTile).getSeedName().toString());
            }

            if (!player.isSneaking() || player.getHeldItem(hand) == null && ConfigHandler.enableRightClickHarvest) {
                AchievementTrigger.triggerAchievement(player, AchievementRegistry.getHarvest);
                return doHarvest(world, pos, player);
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
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return new ArrayList<ItemStack>();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ItemHelper.getItem(ItemSeed.class), 1, getTileSeedIndex(world, pos));
    }

    public void dropItems(World world, BlockPos pos, IBlockState state) {

        TileEntity cropTile = world.getTileEntity(pos);

        if (cropTile instanceof TileRCrop && ((TileRCrop) cropTile).getShouldDrop())
            for (ItemStack stack : getDrops(world, pos, state))
                world.spawnEntityInWorld(new EntityItem(world, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, stack));
    }

    public List<ItemStack> getDrops(World world, BlockPos pos, IBlockState state) {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        int seedIndex = getTileSeedIndex(world, pos);

        if (getAge(state) <= 6) {
            drops.add(new ItemStack(ItemHelper.getItem(ItemSeed.class), 1, seedIndex));
        } else {
            drops.add(new ItemStack(ItemHelper.getItem(ItemSeed.class), 1, seedIndex));
            drops.add(new ItemStack(ItemHelper.getItem(ItemShard.class), 1, seedIndex));
        }
        return drops;
    }

    public boolean doHarvest(World world, BlockPos pos, EntityPlayer player) {
        if (world.getBlockState(pos).getBlock() == BlockHelper.getBlock(BlockRCrop.class) && getAge(world.getBlockState(pos)) >= 7) {
            if (!world.isRemote) {
                world.setBlockState(pos, getDefaultState(), 3);
                doRightClickDrops(world, pos);
            }
            player.swingArm(EnumHand.MAIN_HAND);
            return true;
        }

        return false;
    }

    public boolean doReqInfo(EntityPlayer player, String seedName) {
        Seed seed = ResourcefulAPI.SEEDS.getValue(new ResourceLocation(seedName));

        if (player.getEntityWorld().isRemote)
            return false;

        EntityPlayerMP playerMP = (EntityPlayerMP) player;

        ResourcefulCrops.instance.getTranslater().sendLocalization(playerMP, "chat.ResourcefulCrops.crop.name", seed.getName());
        ResourcefulCrops.instance.getTranslater().sendLocalization(playerMP, "chat.ResourcefulCrops.req.growth", seed.getRequirement().getGrowthReq() != null ? seed.getRequirement().getGrowthReq().getDisplayName() : "info.ResourcefulCrops.anything");

        if (seed.getRequirement().getLightLevelMax() == 15)
            ResourcefulCrops.instance.getTranslater().sendLocalization(playerMP, "chat.ResourcefulCrops.req.light.above", seed.getRequirement().getLightLevelMin());
        else if (seed.getRequirement().getLightLevelMin() == 0)
            ResourcefulCrops.instance.getTranslater().sendLocalization(playerMP, "chat.ResourcefulCrops.req.light.below", seed.getRequirement().getLightLevelMax());
        else
            ResourcefulCrops.instance.getTranslater().sendLocalization(playerMP, "chat.ResourcefulCrops.req.light.between", seed.getRequirement().getLightLevelMin(), seed.getRequirement().getLightLevelMax());

        if (seed.getRequirement().getDifficulty() != EnumDifficulty.PEACEFUL)
            ResourcefulCrops.instance.getTranslater().sendLocalization(playerMP, "chat.ResourcefulCrops.req.difficulty", seed.getRequirement().getDifficulty().toString());

        return true;
    }

    public void doRightClickDrops(World world, BlockPos pos) {

        Random random = new Random();
        int seedIndex = getTileSeedIndex(world, pos);
        Seed seed = ResourcefulAPI.SEEDS.getRaw(seedIndex);

        if (seed == null)
            return;

        world.spawnEntityInWorld(new EntityItem(world, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, new ItemStack(ItemHelper.getItem(ItemShard.class), 1, seedIndex)));

        double extraSeedChance = seed.getChance().getExtraSeed();
        double essenceDropChance = seed.getChance().getEssenceDrop();
        double randomDouble = random.nextDouble();

        if (randomDouble <= extraSeedChance)
            world.spawnEntityInWorld(new EntityItem(world, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, new ItemStack(ItemHelper.getItem(ItemSeed.class), 1, seedIndex)));

        if (randomDouble <= essenceDropChance)
            world.spawnEntityInWorld(new EntityItem(world, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, new ItemStack(ItemHelper.getItem(ItemMaterial.class))));
    }

    @SubscribeEvent
    @Used
    public void onBonemeal(BonemealEvent event) {
        if (event.getBlock().getBlock() instanceof BlockRCrop)
            event.setCanceled(true);
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileRCrop();
    }

    public static int getTileSeedIndex(World world, BlockPos pos) {
        TileEntity crop = world.getTileEntity(pos);
        int seedIndex = Utils.getInvalidSeed(ItemHelper.getItem(ItemSeed.class)).getItemDamage();

        if (crop != null && crop instanceof TileRCrop) {
            ResourceLocation seedName = ((TileRCrop) crop).getSeedName();
            seedIndex = ResourcefulAPI.SEEDS.getId(seedName);
        }

        return seedIndex;
    }
}
