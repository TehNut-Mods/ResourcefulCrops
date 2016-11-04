package tehnut.resourceful.crops.compat.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tehnut.lib.util.helper.ItemHelper;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.crops.item.ItemSeed;
import tehnut.resourceful.crops.tile.TileRCrop;
import tehnut.resourceful.crops.util.Utils;

import java.util.List;

public class ResourcefulCropsDataProvider implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {

        if (accessor.getBlock() instanceof BlockRCrop) {
            TileEntity cropTile = accessor.getTileEntity();

            if (cropTile != null && cropTile instanceof TileRCrop) {
                Seed seed = ResourcefulAPI.SEEDS.getObject(((TileRCrop) cropTile).getSeedName());
                if (Utils.isValidSeed(seed)) {
                    if (config.getConfig("outputStack") && !seed.getOutput().isEmpty()) {
                        ItemStack outputCopy = seed.getOutput().get(0).getOutputStack().copy();
                        outputCopy.stackSize = 1;
                        return outputCopy;
                    }
                    else
                        return new ItemStack(ItemHelper.getItem(ItemSeed.class), 1, ResourcefulAPI.SEEDS.getId(seed));
                }
            }
        }

        return Utils.getInvalidSeed(ItemHelper.getItem(ItemSeed.class));
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        return null;
    }
}
