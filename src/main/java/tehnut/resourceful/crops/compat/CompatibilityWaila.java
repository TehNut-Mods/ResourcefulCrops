package tehnut.resourceful.crops.compat;

import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import tehnut.resourceful.crops.block.tile.TileSeedContainer;
import tehnut.resourceful.crops.core.RegistrarResourcefulCrops;
import tehnut.resourceful.crops.core.data.Seed;

import javax.annotation.Nonnull;
import java.util.List;

@WailaPlugin
public class CompatibilityWaila implements IWailaPlugin {

    @Override
    public void register(IWailaRegistrar registrar) {
        final CropProvider cropProvider = new CropProvider();
        registrar.registerStackProvider(cropProvider, TileSeedContainer.class);
        registrar.registerNBTProvider(cropProvider, TileSeedContainer.class);
    }

    public static class CropProvider implements IWailaDataProvider {
        @Nonnull
        @Override
        public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
            Seed seed = RegistrarResourcefulCrops.SEEDS.getValue(new ResourceLocation(accessor.getNBTData().getString("seed")));
            if (seed == null)
                return accessor.getStack();

            if (seed.isNull())
                return accessor.getStack();

            if (seed.getOutputs().length == 0)
                return accessor.getStack();

            return ItemHandlerHelper.copyStackWithSize(seed.getOutputs()[0].getItem(), 1);
        }

        @Nonnull
        @Override
        public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
            return currenttip;
        }

        @Nonnull
        @Override
        public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
            return currenttip;
        }

        @Nonnull
        @Override
        public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
            return currenttip;
        }

        @Nonnull
        @Override
        public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
            tag.setString("seed", ((TileSeedContainer) te).getSeedKey().toString());
            return tag;
        }
    }
}
