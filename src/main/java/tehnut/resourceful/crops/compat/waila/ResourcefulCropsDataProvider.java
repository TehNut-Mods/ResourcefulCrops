package tehnut.resourceful.crops.compat.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tehnut.resourceful.crops.base.Seed;
import tehnut.resourceful.crops.blocks.BlockRCrop;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.crops.registry.SeedRegistry;
import tehnut.resourceful.crops.tile.TileRCrop;
import tehnut.resourceful.crops.util.Utils;

import java.util.List;

public class ResourcefulCropsDataProvider implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {

        if (accessor.getBlock() instanceof BlockRCrop) {
            TileEntity cropTile = accessor.getTileEntity();

            if (cropTile != null && cropTile instanceof TileRCrop) {
                Seed seed = SeedRegistry.getSeed(((TileRCrop) cropTile).getSeedName());
                if (Utils.isValidSeed(seed))
                    return new ItemStack(ItemRegistry.seed, 1, SeedRegistry.getIndexOf(seed));
            }
        }

        return new ItemStack(ItemRegistry.seed, 1, SeedRegistry.getSize() + 1);
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
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        return null;
    }
}
