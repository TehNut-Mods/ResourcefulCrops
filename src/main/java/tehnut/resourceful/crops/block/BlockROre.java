package tehnut.resourceful.crops.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import tehnut.resourceful.crops.ModInformation;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.registry.ItemRegistry;

import java.util.List;
import java.util.Random;

public class BlockROre extends Block {

    Random random = new Random();

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
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ItemRegistry.material;
    }

    @Override
    public int quantityDropped(Random random) {

        int drop = random.nextInt(4);

        return drop != 0 ? drop : 1;
    }

    @Override
    public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune) {
        return random.nextInt(4);
    }
}
