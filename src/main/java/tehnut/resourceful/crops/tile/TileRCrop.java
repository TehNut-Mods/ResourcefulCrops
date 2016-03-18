package tehnut.resourceful.crops.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileRCrop extends TileEntity {

    private String seedName = "Dead";
    private boolean shouldDrop = true;

    public String getSeedName() {
        return seedName;
    }

    public void setSeedName(String seedName) {
        this.seedName = seedName;
    }

    public boolean getShouldDrop() {
        return shouldDrop;
    }

    public void setShouldDrop(boolean shouldDrop) {
        this.shouldDrop = shouldDrop;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        tag.setString("seedName", getSeedName());

        super.writeToNBT(tag);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        setSeedName(tag.getString("seedName"));

        super.readFromNBT(tag);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.writeToNBT(tagCompound);

        return new SPacketUpdateTileEntity(this.getPos(), 1, tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
    }
}
