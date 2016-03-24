package tehnut.resourceful.crops.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import tehnut.lib.annot.ModBlock;
import tehnut.lib.block.base.BlockString;
import tehnut.lib.block.item.ItemBlockString;
import tehnut.lib.iface.IVariantProvider;
import tehnut.lib.util.helper.BlockHelper;
import tehnut.lib.util.helper.ItemHelper;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.item.ItemMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ModBlock(name = "BlockROre", itemBlock = ItemBlockString.class)
public class BlockROre extends BlockString implements IVariantProvider {

    public static final String[] NAMES = {"normal", "nether"};
    Random random = new Random();

    public BlockROre() {
        super(Material.rock, NAMES, "type");

        setUnlocalizedName(ModInformation.ID + ".ore");
        setSoundType(SoundType.STONE);
        setHardness(4.0F);
        setCreativeTab(ResourcefulCrops.tabResourcefulCrops);
        setHarvestLevel("pickaxe", 3);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tile, ItemStack stack) {
        if (player instanceof FakePlayer && !ConfigHandler.enableFakePlayerMining) {
            Block blockItem = Blocks.cobblestone;
            dropItem(world, pos, new ItemStack(blockItem, 1, 0));
        } else {
            boolean silk = EnchantmentHelper.getEnchantmentLevel(Enchantments.silkTouch, player.getHeldItemMainhand()) > 0;
            if (silk) {
                Block ore = BlockHelper.getBlock(BlockROre.class);
                dropItem(world, pos, new ItemStack(ore, 1, getMetaFromState(state)));
            } else {
                int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.fortune, player.getHeldItemMainhand());
                int dropAmount;

                if (fortune > 0) {
                    int bonus = random.nextInt(fortune + 2) - 1;

                    if (bonus < 0)
                        bonus = 0;

                    dropAmount = bonus + 1;
                } else {
                    dropAmount = 1;
                }

                Item droppedItem = ItemHelper.getItem(ItemMaterial.class);
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
        return ItemHelper.getItem(ItemMaterial.class);
    }

    @Override
    public int quantityDropped(Random random) {
        int drop = random.nextInt(4);

        return drop != 0 ? drop : 1;
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
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

    @Override
    public List<Pair<Integer, String>> getVariants() {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        ret.add(new ImmutablePair<Integer, String>(0, "type=normal"));
        ret.add(new ImmutablePair<Integer, String>(1, "type=nether"));
        return ret;
    }
}
