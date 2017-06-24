package tehnut.resourceful.crops.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.core.RegistrarResourcefulCrops;

import java.util.Random;

public class BlockGaianiteOre extends Block {

    public static final PropertyBool NETHER = PropertyBool.create("nether");

    public BlockGaianiteOre() {
        super(Material.ROCK);

        setUnlocalizedName(ResourcefulCrops.MODID + ".ore");
        setCreativeTab(ResourcefulCrops.TAB_RCROP);
        setSoundType(SoundType.STONE);
        setHardness(4.0F);
        setHarvestLevel("pickaxe", 3);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this).add(NETHER).build();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(NETHER, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(NETHER) ? 1 : 0;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(state.getBlock(), 1, getMetaFromState(state));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return RegistrarResourcefulCrops.ESSENCE;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        int randBound = 2 * ((fortune + 1) / 2);
        int drop = random.nextInt(randBound <= 0 ? 1 : randBound);
        return drop == 0 ? 1 : drop;
    }
}
