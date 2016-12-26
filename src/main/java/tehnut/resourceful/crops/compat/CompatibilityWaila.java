package tehnut.resourceful.crops.compat;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.resourceful.crops.block.BlockResourcefulCrop;
import tehnut.resourceful.crops.block.tile.TileSeedContainer;
import tehnut.resourceful.crops.core.ModObjects;
import tehnut.resourceful.crops.core.data.Seed;
import tehnut.resourceful.crops.util.Util;

import java.util.List;

@Compatibility(modid = "waila")
public class CompatibilityWaila implements ICompatibility {

    @Override
    public void loadCompatibility() {
        FMLInterModComms.sendMessage("waila", "register", "tehnut.resourceful.crops.compat.CompatibilityWaila.wailaCallback");
    }

    public static void wailaCallback(IWailaRegistrar registrar) {
        IWailaDataProvider cropProvider = new IWailaDataProvider() {
            @Override
            public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
                Seed seed = ModObjects.SEEDS.getValue(new ResourceLocation(accessor.getNBTData().getString("seed")));
                if (seed == null)
                    return ItemStack.EMPTY;

                ItemStack ret = seed.getOutputs()[0].getItem();
                ret.setCount(1);

                return ret;
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
                tag.setString("seed", ((TileSeedContainer) te).getSeedKey().toString());
                return tag;
            }
        };

        registrar.registerStackProvider(cropProvider, BlockResourcefulCrop.class);
        registrar.registerNBTProvider(cropProvider, BlockResourcefulCrop.class);
    }
}
