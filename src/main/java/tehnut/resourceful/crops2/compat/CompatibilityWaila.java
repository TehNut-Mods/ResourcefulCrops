package tehnut.resourceful.crops2.compat;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.resourceful.crops2.block.BlockResourcefulCrop;
import tehnut.resourceful.crops2.block.tile.TileSeedContainer;
import tehnut.resourceful.crops2.core.ModObjects;
import tehnut.resourceful.crops2.core.data.Seed;

import java.util.List;

@Compatibility(modid = "Waila")
public class CompatibilityWaila implements ICompatibility {

    @Override
    public void loadCompatibility() {
        FMLInterModComms.sendMessage("Waila", "register", "tehnut.resourceful.crops2.compat.CompatibilityWaila.wailaCallback");
    }

    public static void wailaCallback(IWailaRegistrar registrar) {
        registrar.registerStackProvider(new IWailaDataProvider() {
            @Override
            public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
                ItemStack invalid = new ItemStack(Blocks.BARRIER);
                invalid.setStackDisplayName(new TextComponentTranslation("info.resourcefulcrops.invalid").getFormattedText());

                TileEntity tile = accessor.getTileEntity();
                if (tile != null && tile instanceof TileSeedContainer) {
                    TileSeedContainer seedContainer = (TileSeedContainer) tile;
                    if (seedContainer.getSeedKey() == null)
                        return invalid;

                    Seed seed = ModObjects.SEEDS.getValue(seedContainer.getSeedKey());
                    ItemStack output = seed.getOutputItems()[0];
                    if (output != null)
                        output.stackSize = 1;
                    return output != null ? output : invalid;
                }
                return invalid;
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
        }, BlockResourcefulCrop.class);
    }
}
