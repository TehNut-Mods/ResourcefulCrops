//package tehnut.resourceful.crops.compat.bloodmagic;
//
//import WayofTime.bloodmagic.api.BlockStack;
//import WayofTime.bloodmagic.api.iface.IHarvestHandler;
//import net.minecraft.block.Block;
//import net.minecraft.entity.item.EntityItem;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import tehnut.resourceful.crops.api.registry.SeedRegistry;
//import tehnut.resourceful.crops.block.BlockRCrop;
//import tehnut.resourceful.crops.item.ItemShard;
//import tehnut.resourceful.crops.registry.BlockRegistry;
//import tehnut.resourceful.crops.registry.ItemRegistry;
//import tehnut.resourceful.crops.tile.TileRCrop;
//
//public class ResourcefulCropsHarvestHandler implements IHarvestHandler {
//
//    @Override
//    public boolean harvestAndPlant(World world, BlockPos blockPos, BlockStack blockStack) {
//        if (blockStack.getBlock() instanceof BlockRCrop && blockStack.getMeta() >= 7) {
//            TileEntity tile = world.getTileEntity(blockPos);
//            if (tile != null && tile instanceof TileRCrop) {
//                TileRCrop tileCrop = (TileRCrop) tile;
//                BlockStack newBlock = new BlockStack(BlockRegistry.getBlock(BlockRCrop.class));
//                world.setBlockState(blockPos, newBlock.getState());
//                world.playAuxSFX(2001, blockPos, Block.getIdFromBlock(blockStack.getBlock()) + (blockStack.getMeta() << 12));
//                ItemStack drop = new ItemStack(ItemRegistry.getItem(ItemShard.class), 1, SeedRegistry.getIndexOf(tileCrop.getSeedName()));
//                dropItem(world, blockPos, drop);
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    private void dropItem(World world, BlockPos pos, ItemStack stack) {
//        if (!world.isRemote && !world.restoringBlockSnapshots) {
//            float f = 0.7F;
//            double xOff = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
//            double yOff = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
//            double zOff = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
//            EntityItem entityitem = new EntityItem(world, (double) pos.getX() + xOff, (double) pos.getY() + yOff, (double) pos.getZ() + zOff, stack);
//            entityitem.setPickupDelay(10);
//            world.spawnEntityInWorld(entityitem);
//        }
//    }
//}
