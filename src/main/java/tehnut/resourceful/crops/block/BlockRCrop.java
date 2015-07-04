package tehnut.resourceful.crops.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockRCrop extends BlockCrops implements ITileEntityProvider {

    public IIcon[] cropIcons = new IIcon[8];
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
                    float f = func_149864_n(world, x, y, z);

                    if (random.nextInt((int)(25.0F / f) + 1) == 0) {
                        ++meta;
                        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
                    }
                }
            }
        }
    }

    // Ripped from Minecraft since it's private :(
    private float func_149864_n(World world, int x, int y, int z) {
        float f = 1.0F;
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

        for (int l = x - 1; l <= x + 1; ++l) {
            for (int i1 = z - 1; i1 <= z + 1; ++i1) {
                float f1 = 0.0F;

                if (world.getBlock(l, y - 1, i1).canSustainPlant(world, l, y - 1, i1, ForgeDirection.UP, this)) {
                    f1 = 1.0F;

                    if (world.getBlock(l, y - 1, i1).isFertile(world, l, y - 1, i1))
                        f1 = 3.0F;
                }

                if (l != x || i1 != z)
                    f1 /= 4.0F;

                f += f1;
            }
        }

        if (flag2 || flag && flag1)
            f /= 2.0F;

        return f;
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

        if (!world.isRemote)
            player.addChatComponentMessage(new ChatComponentText(String.valueOf(world.getBlockLightValue(x, y + 1, z))));

        TileEntity cropTile = world.getTileEntity(x, y, z);

        if (!player.isSneaking() || player.getHeldItem() == null && Utils.isValidSeed(((TileRCrop)cropTile).getSeedName()) && ConfigHandler.enableRightClickHarvest) {
            if (world.getBlock(x, y, z) == BlockRegistry.crop && world.getBlockMetadata(x, y, z) >= 7) {
                if (!world.isRemote) {
                    world.setBlockMetadataWithNotify(x, y, z, 0, 3);
                    dropBlockAsItem(world, x, y, z, new ItemStack(ItemRegistry.shard, 1, getTileSeedIndex(world, x, y, z)));
                }
                player.swingItem();
                return true;
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
            if (ConfigHandler.enableFancyRender) {
                cropIcons[i] = ir.registerIcon(ModInformation.ID + ":crop_base_" + i);
                cropOverlay[i] = ir.registerIcon(ModInformation.ID + ":crop_overlay_" + i);
            } else {
                cropIcons[i] = ir.registerIcon(ModInformation.ID + ":crop_base_" + i + "_fast");
            }
        }

        blockIcon = cropIcons[4];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return cropIcons[meta];
    }

    @Override
    public int getRenderType() {
        return ConfigHandler.enableFancyRender ? ResourcefulCrops.renderIDCrop : 1;
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