package tehnut.resourceful.crops.compat.bloodmagic;

import WayofTime.alchemicalWizardry.api.harvest.IHarvestHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tehnut.resourceful.crops.blocks.BlockRCrop;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.crops.registry.SeedRegistry;
import tehnut.resourceful.crops.tile.TileRCrop;

public class ResourcefulCropsHarvestHandler implements IHarvestHandler {

    @Override
    public boolean harvestAndPlant(World world, int x, int y, int z, Block block, int meta) {
        if (block instanceof BlockRCrop) {
            TileEntity cropTile = world.getTileEntity(x, y, z);
            if (cropTile != null && cropTile instanceof TileRCrop) {
                if (meta == 7) {
                    world.setBlockMetadataWithNotify(x, y, z, 0, 3);
                    world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
                    ItemStack seedStack = new ItemStack(ItemRegistry.shard, 1, SeedRegistry.getIndexOf(((TileRCrop) cropTile).getSeedName()));
                    dropItem(world, x, y, z, seedStack);
                    return true;
                }
            }
        }

        return false;
    }

    private void dropItem(World world, int x, int y, int z, ItemStack stack) {
        if (!world.isRemote && !world.restoringBlockSnapshots) {
            float f = 0.7F;
            double xOff = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double yOff = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double zOff = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double)x + xOff, (double)y + yOff, (double)z + zOff, stack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }
}
