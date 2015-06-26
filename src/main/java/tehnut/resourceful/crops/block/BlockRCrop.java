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
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.ModInformation;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.crops.registry.SeedRegistry;
import tehnut.resourceful.crops.tile.TileRCrop;
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
        if (getTileSeedIndex(world, x, y, z) == Short.MAX_VALUE)
            return;

        super.updateTick(world, x, y, z, random);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
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
