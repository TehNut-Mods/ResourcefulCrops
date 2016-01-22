package tehnut.resourceful.crops.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.annot.ModBlock;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.item.ItemMaterial;
import tehnut.resourceful.crops.item.block.ItemBlockROre;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.crops.registry.ItemRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ModBlock(name = "BlockROre", itemBlock = ItemBlockROre.class)
public class BlockROre extends Block {

    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 1);
    Random random = new Random();

    public BlockROre() {
        super(Material.rock);

        setUnlocalizedName(ModInformation.ID + ".ore");
        setStepSound(soundTypeStone);
        setHardness(4.0F);
        setCreativeTab(ResourcefulCrops.tabResourcefulCrops);
        setHarvestLevel("pickaxe", 3);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item id, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < 2; i++)
            list.add(new ItemStack(id, 1, i));
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, this.getMetaFromState(world.getBlockState(pos)));
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tile) {

        if (player instanceof FakePlayer && !ConfigHandler.enableFakePlayerMining) {
            Block blockItem = Blocks.cobblestone;
            dropItem(world, pos, new ItemStack(blockItem, 1, 0));
        } else {
            boolean silk = EnchantmentHelper.getSilkTouchModifier(player);
            if (silk) {
                Block ore = BlockRegistry.getBlock(BlockROre.class);
                dropItem(world, pos, new ItemStack(ore, 1, getMetaFromState(state)));
            } else {
                int fortune = EnchantmentHelper.getFortuneModifier(player);
                int dropAmount;

                if (fortune > 0) {
                    int bonus = random.nextInt(fortune + 2) - 1;

                    if (bonus < 0)
                        bonus = 0;

                    dropAmount = bonus + 1;
                } else {
                    dropAmount = 1;
                }

                Item droppedItem = ItemRegistry.getItem(ItemMaterial.class);
                dropItem(world, pos, new ItemStack(droppedItem, dropAmount, 0));
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        Random random = new Random();

        int count = quantityDropped(state, fortune, random);
        for (int i = 0; i < count; i++) {
            Item item = getItemDropped(state, random, fortune);
            if (item != null) {
                if (ConfigHandler.enableFakePlayerMining)
                    ret.add(new ItemStack(item, 1, damageDropped(state)));
            }
        }

        return ret;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ItemRegistry.getItem(ItemMaterial.class);
    }

    @Override
    public int quantityDropped(Random random) {
        int drop = random.nextInt(4);

        return drop != 0 ? drop : 1;
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(META, meta);
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(META);
    }

    protected BlockState createBlockState() {
        return new BlockState(this, META);
    }

    @Override
    public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune) {
        return random.nextInt(4);
    }

    public static EntityItem dropItem(World world, BlockPos pos, ItemStack stack) {
        if (stack == null)
            return null;

        EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack.copy());
        entityItem.setPickupDelay(10);
        if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops") && !world.restoringBlockSnapshots)
            world.spawnEntityInWorld(entityItem);

        return entityItem;
    }
}
