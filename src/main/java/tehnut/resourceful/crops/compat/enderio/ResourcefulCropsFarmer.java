package tehnut.resourceful.crops.compat.enderio;

import crazypants.enderio.machine.farm.TileFarmStation;
import crazypants.enderio.machine.farm.farmers.HarvestResult;
import crazypants.enderio.machine.farm.farmers.IHarvestResult;
import crazypants.enderio.machine.farm.farmers.PlantableFarmer;
import crazypants.util.BlockCoord;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tehnut.resourceful.crops.blocks.BlockRCrop;
import tehnut.resourceful.crops.items.ItemSeed;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.crops.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class ResourcefulCropsFarmer extends PlantableFarmer {

    @Override
    public boolean canHarvest(TileFarmStation farmStation, BlockCoord coord, Block block, int meta) {
        return block instanceof BlockRCrop && farmStation.getWorldObj().getBlockMetadata(coord.x, coord.y, coord.z) == 7;
    }

    @Override
    public boolean canPlant(ItemStack stack) {
        return stack != null && stack.getItem() instanceof ItemSeed && Utils.getItemDamage(stack) != Short.MAX_VALUE;
    }

    @Override
    public boolean prepareBlock(TileFarmStation farmStation, BlockCoord coord, Block block, int meta) {
        if (block == null)
            return false;

        ItemStack seedStack = farmStation.getSeedTypeInSuppliesFor(coord);
        if (seedStack == null)
            return false;

        if (!canPlant(seedStack))
            return false;

        if (!farmStation.hasHoe())
            return false;

        Block plantOn = farmStation.getBlock(coord.getLocation(ForgeDirection.DOWN));

        if (!block.canSustainPlant(farmStation.getWorldObj(), coord.x, coord.y, coord.z, ForgeDirection.UP, ItemRegistry.seed) && (plantOn == Blocks.dirt || plantOn == Blocks.grass)) {
            farmStation.getWorldObj().setBlock(coord.x, coord.y - 1, coord.z, Blocks.farmland);
            farmStation.damageHoe(1, coord);
        }


        Item seed = seedStack.getItem();

        if (seed.onItemUse(seedStack, farmStation.getFakePlayer(), farmStation.getWorldObj(), coord.x, coord.y - 1, coord.z, 1, 0.5F, 0.5F, 0.5F))
            farmStation.takeSeedFromSupplies(coord);

        return false;
    }

    @Override
    public IHarvestResult harvestBlock(TileFarmStation farmStation, BlockCoord coord, Block block, int meta) {
        if (!canHarvest(farmStation, coord, block, meta) || !farmStation.hasHoe() || !(block instanceof BlockRCrop))
            return null;

        List<ItemStack> dropped = ((BlockRCrop)block).getDrops(farmStation.getWorldObj(), coord.x, coord.y, coord.z, meta);
        BlockRCrop.setShouldDrop(false);
        farmStation.getWorldObj().setBlockToAir(coord.x, coord.y, coord.z);
        BlockRCrop.setShouldDrop(true);
        return new HarvestResult(getEntities(farmStation.getWorldObj(), coord, dropped), coord);
    }

    private List<EntityItem> getEntities(World world, BlockCoord bc,  List<ItemStack> dropped) {
        List<EntityItem> entities = new ArrayList<EntityItem>(dropped.size());
        for (ItemStack stack : dropped)
            entities.add(new EntityItem(world, bc.x + 0.5, bc.y + 0.2, bc.z + 0.5, stack));

        return entities;
    }
}
