package tehnut.resourceful.crops.compat.enderio;

import com.enderio.core.common.util.BlockCoord;
import crazypants.enderio.machine.farm.FarmNotification;
import crazypants.enderio.machine.farm.TileFarmStation;
import crazypants.enderio.machine.farm.farmers.HarvestResult;
import crazypants.enderio.machine.farm.farmers.IHarvestResult;
import crazypants.enderio.machine.farm.farmers.PlantableFarmer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.ForgeEventFactory;
import tehnut.resourceful.crops.block.tile.TileSeedContainer;
import tehnut.resourceful.crops.core.data.Seed;
import tehnut.resourceful.crops.item.ItemResourceful;
import tehnut.resourceful.crops.item.ItemResourcefulSeed;

import java.util.ArrayList;
import java.util.List;

public class ResourcefulFarmer extends PlantableFarmer {

    @Override
    protected boolean plantFromInventory(TileFarmStation farm, BlockCoord bc, IPlantable plantable) {
        World worldObj = farm.getWorld();
        ItemStack seedStack = farm.takeSeedFromSupplies(bc);
        if(canPlant(worldObj, bc, plantable) && seedStack != null) {
            return plant(farm, worldObj, bc, seedStack, plantable);
        }
        return false;
    }

    public boolean plant(TileFarmStation farm, World worldObj, BlockCoord bc, ItemStack seedStack, IPlantable plantable) {
        if (seedStack == null || !(seedStack.getItem() instanceof ItemResourcefulSeed))
            return false;
        Seed seed = ((ItemResourceful) seedStack.getItem()).getSeed(seedStack);
        if (seed == null)
            return false;
        worldObj.setBlockState(bc.getBlockPos(), Blocks.AIR.getDefaultState(), 1 | 2);
        IBlockState target = plantable.getPlant(null, bc.getBlockPos());
        worldObj.setBlockState(bc.getBlockPos(), target, 1 | 2);
        worldObj.setTileEntity(bc.getBlockPos(), new TileSeedContainer(seed.getRegistryName()));
        farm.actionPerformed(false);
        return true;
    }

    // Copied directly from PlantableFarmer. Only one line is changed to use the stack-sensitive plant(...) method.
    @Override
    public IHarvestResult harvestBlock(TileFarmStation farm, BlockCoord bc, Block block, IBlockState meta) {
        if(!canHarvest(farm, bc, block, meta)) {
            return null;
        }
        if(!farm.hasHoe()) {
            farm.setNotification(FarmNotification.NO_HOE);
            return null;
        }

        World worldObj = farm.getWorld();
        List<EntityItem> result = new ArrayList<EntityItem>();
        final EntityPlayerMP fakePlayer = farm.getFakePlayer();
        final int fortune = farm.getMaxLootingValue();

        ItemStack removedPlantable = null;

        List<ItemStack> drops = block.getDrops(worldObj, bc.getBlockPos(), meta, fortune);
        float chance = ForgeEventFactory.fireBlockHarvesting(drops, worldObj, bc.getBlockPos(), meta, fortune, 1.0F, false, fakePlayer);
        farm.damageHoe(1, bc);
        farm.actionPerformed(false);
        boolean removed = false;
        if(drops != null) {
            for (ItemStack stack : drops) {
                if (stack != null && stack.stackSize > 0 && worldObj.rand.nextFloat() <= chance) {
                    if (!removed && isPlantableForBlock(stack, block)) {
                        removed = true;
                        removedPlantable = stack.copy();
                        removedPlantable.stackSize = 1;
                        stack.stackSize--;
                        if (stack.stackSize > 0) {
                            result.add(new EntityItem(worldObj, bc.x + 0.5, bc.y + 0.5, bc.z + 0.5, stack.copy()));
                        }
                    } else {
                        result.add(new EntityItem(worldObj, bc.x + 0.5, bc.y + 0.5, bc.z + 0.5, stack.copy()));
                    }
                }
            }
        }

        ItemStack[] inv = fakePlayer.inventory.mainInventory;
        for (int slot = 0; slot < inv.length; slot++) {
            ItemStack stack = inv[slot];
            if (stack != null) {
                inv[slot] = null;
                EntityItem entityitem = new EntityItem(worldObj, bc.x + 0.5, bc.y + 1, bc.z + 0.5, stack);
                result.add(entityitem);
            }
        }

        if(removed) {
            if(!plant(farm, worldObj, bc, removedPlantable, (IPlantable) removedPlantable.getItem())) { // Modified to use stack-sensitive plant(...) method.
                result.add(new EntityItem(worldObj, bc.x + 0.5, bc.y + 0.5, bc.z + 0.5, removedPlantable.copy()));
                worldObj.setBlockState(bc.getBlockPos(), Blocks.AIR.getDefaultState(), 1 | 2);
            }
        } else {
            worldObj.setBlockState(bc.getBlockPos(), Blocks.AIR.getDefaultState(), 1 | 2);
        }

        return new HarvestResult(result, bc.getBlockPos());
    }

    // Copied directly from PlantableFarmer because it's private.
    private boolean isPlantableForBlock(ItemStack stack, Block block) {
        if(!(stack.getItem() instanceof IPlantable)) {
            return false;
        }
        IPlantable plantable = (IPlantable) stack.getItem();
        IBlockState b = plantable.getPlant(null, new BlockPos(0, 0, 0));
        return b != null && b.getBlock() == block;
    }
}
