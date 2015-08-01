package tehnut.resourceful.crops.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.registry.ItemRegistry;

import java.util.List;
import java.util.Random;

public class BlockROre extends Block {

    Random random = new Random();
    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 1);

    public BlockROre() {
        super(Material.rock);

        setUnlocalizedName(ModInformation.ID + ".ore");
        setStepSound(soundTypeStone);
        setHardness(4.0F);
        setCreativeTab(ResourcefulCrops.tabResourcefulCrops);
        setHarvestLevel("pickaxe", 3);
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item id, CreativeTabs tab, List list) {
        for (int i = 0; i < 2; i++)
            list.add(new ItemStack(id, 1, i));
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, this.getMetaFromState(world.getBlockState(pos)));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ItemRegistry.material;
    }

    @Override
    public int quantityDropped(Random random) {

        int drop = random.nextInt(4);

        return drop != 0 ? drop : 1;
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(META, Integer.valueOf(meta));
    }

    public int getMetaFromState(IBlockState state) {
        return ((Integer)state.getValue(META)).intValue();
    }

    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {META});
    }

    @Override
    public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune) {
        return random.nextInt(4);
    }
}
