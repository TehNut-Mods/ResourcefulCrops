package tehnut.resourceful.crops.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.crops.registry.SeedRegistry;
import tehnut.resourceful.crops.tile.TileRCrop;
import tehnut.resourceful.crops.api.util.BlockStack;
import tehnut.resourceful.crops.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockRCrop extends BlockCrops implements ITileEntityProvider {

    public IIcon[] cropIcons = new IIcon[8];
    public IIcon[] fastIcons = new IIcon[8];
    public IIcon[] cropOverlay = new IIcon[8];
    private static boolean shouldDrop = true;

    public BlockRCrop() {
        super();

        setBlockName(ModInformation.ID + ".crop");
    }

    public static int getTileSeedIndex(World world, int x, int y, int z) {
        TileEntity crop = world.getTileEntity(x, y, z);
        int seedIndex = Utils.getInvalidSeed(ItemRegistry.seed).getItemDamage();

        if (crop != null && crop instanceof TileRCrop) {
            String seedName = ((TileRCrop) crop).getSeedName();
            seedIndex = SeedRegistry.getIndexOf(seedName);
        }

        return seedIndex;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {

        int seedIndex = getTileSeedIndex(world, x, y, z);
        Seed seed = SeedRegistry.getSeed(seedIndex);

        if (seedIndex == Short.MAX_VALUE)
            return;

        BlockStack blockReq = new BlockStack(world.getBlock(x, y - 2, z), world.getBlockMetadata(x, y - 2, z));

        checkAndDropBlock(world, x, y, z);

        if (seed.getSeedReq().getGrowthReq() == null || seed.getSeedReq().getGrowthReq().equals(blockReq)) {

            int lightLevel = world.getBlockLightValue(x, y + 1, z);

            if (lightLevel >= seed.getSeedReq().getLightLevelMin() && lightLevel <= seed.getSeedReq().getLightLevelMax()) {
                int meta = world.getBlockMetadata(x, y, z);

                if (meta < 7) {
                    float growthChance = getGrowthChance(world, x, y, z);

                    if (random.nextInt((int)(25.0F / growthChance) + 1) == 0) {
                        ++meta;
                        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
                    }
                }
            }
        }
    }

    // Ripped from Minecraft since it's private until 1.8 :(
    private float getGrowthChance(World world, int x, int y, int z) {
        float growthChance = 1.0F;
        Block block = world.getBlock(x, y, z - 1);
        Block block1 = world.getBlock(x, y, z + 1);
        Block block2 = world.getBlock(x - 1, y, z);
        Block block3 = world.getBlock(x + 1, y, z);
        Block block4 = world.getBlock(x - 1, y, z - 1);
        Block block5 = world.getBlock(x + 1, y, z - 1);
        Block block6 = world.getBlock(x + 1, y, z + 1);
        Block block7 = world.getBlock(x - 1, y, z + 1);
        boolean flag = block2 == this || block3 == this;
        boolean flag1 = block == this || block1 == this;
        boolean flag2 = block4 == this || block5 == this || block6 == this || block7 == this;

        for (int coordX = x - 1; coordX <= x + 1; ++coordX) {
            for (int coordZ = z - 1; coordZ <= z + 1; ++coordZ) {
                float chance = 0.0F;

                if (world.getBlock(coordX, y - 1, coordZ).canSustainPlant(world, coordX, y - 1, coordZ, ForgeDirection.UP, this)) {
                    chance = 1.0F;

                    if (world.getBlock(coordX, y - 1, coordZ).isFertile(world, coordX, y - 1, coordZ))
                        chance = 3.0F;
                }

                if (coordX != x || coordZ != z)
                    chance /= 4.0F;

                growthChance += chance;
            }
        }

        if (flag2 || flag && flag1)
            growthChance /= 2.0F;

        return growthChance;
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity cropTile = world.getTileEntity(x, y, z);

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
                if (world.getBlock(x, y, z) == BlockRegistry.crop && world.getBlockMetadata(x, y, z) >= 7) {
                    if (!world.isRemote) {
                        world.setBlockMetadataWithNotify(x, y, z, 0, 3);
                        dropBlockAsItem(world, x, y, z, new ItemStack(ItemRegistry.shard, 1, getTileSeedIndex(world, x, y, z)));
                    }
                    player.swingItem();
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        dropItems(world, x, y, z, meta);

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int p_149650_3_) {
        return null;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
        return new ArrayList<ItemStack>();
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        TileEntity crop = world.getTileEntity(x, y, z);

        if (crop != null && crop instanceof TileRCrop)
            return new ItemStack(ItemRegistry.seed, 1, getTileSeedIndex(world, x, y, z));

        return Utils.getInvalidSeed(ItemRegistry.seed);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        for (int i = 0; i < cropIcons.length; i++) {
            cropIcons[i] = ir.registerIcon(ModInformation.TEXLOC + "crop_base_" + i);
            cropOverlay[i] = ir.registerIcon(ModInformation.TEXLOC + "crop_overlay_" + i);
            fastIcons[i] = ir.registerIcon(ModInformation.TEXLOC + "crop_base_" + i + "_fast");
        }

        blockIcon = cropIcons[4];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return ConfigHandler.enableFancyRender || ConfigHandler.forceFancyRender ? cropIcons[meta] : fastIcons[meta];
    }

    @Override
    public int getRenderType() {
        return ConfigHandler.enableFancyRender || ConfigHandler.forceFancyRender ? ResourcefulCrops.renderIDCrop : 1;
    }

    @Override
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {

        TileEntity cropTile = blockAccess.getTileEntity(x, y, z);

        if (cropTile instanceof TileRCrop && !ConfigHandler.enableFancyRender)
            return SeedRegistry.getSeed(((TileRCrop) cropTile).getSeedName()).getColor().getRGB();

        return 16777215;
    }

    public void dropItems(World world, int x, int y, int z, int meta) {
        if (shouldDrop)
            for (ItemStack stack : getDrops(world, x, y, z, meta))
                dropBlockAsItem(world, x, y, z, stack);
    }

    public List<ItemStack> getDrops(World world, int x, int y, int z, int meta) {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        int seedIndex = getTileSeedIndex(world, x, y, z);

        if (meta <= 6) {
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
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Crop;
    }

    // ITileEntityProvider

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileRCrop();
    }
}
