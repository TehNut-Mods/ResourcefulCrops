package tehnut.resourceful.crops.block.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class TileSeedContainer extends TileEntity {

    @Nullable
    private ResourceLocation seedKey;

    public TileSeedContainer() {

    }

    public TileSeedContainer(ResourceLocation seedKey) {
        this.seedKey = seedKey;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.seedKey = compound.hasKey("seedKey") ? new ResourceLocation(compound.getString("seedKey")) : null;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if (seedKey != null)
            compound.setString("seedKey", seedKey.toString());
        return super.writeToNBT(compound);
    }

    @Override
    public boolean restrictNBTCopy() {
        return true;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public final SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), -999, writeToNBT(new NBTTagCompound()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public final NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public final void handleUpdateTag(NBTTagCompound tag) {
        readFromNBT(tag);
    }

    @Nullable
    public ResourceLocation getSeedKey() {
        return seedKey;
    }

    public void setSeedKey(@Nullable ResourceLocation seedKey) {
        this.seedKey = seedKey;
        notifyUpdate();
    }

    public void notifyUpdate() {
        getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
    }
}
