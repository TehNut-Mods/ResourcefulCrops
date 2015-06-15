package tehnut.resourceful.crops.compat.mfr;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.api.ReplacementBlock;
import tehnut.resourceful.crops.blocks.BlockRCrop;
import tehnut.resourceful.crops.items.ItemSeed;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.crops.registry.SeedRegistry;
import tehnut.resourceful.crops.tile.TileRCrop;
import tehnut.resourceful.crops.util.Utils;

public class PlantableHandler implements IFactoryPlantable {

    // IFactoryPlantable

    @Override
    public Item getSeed() {
        return ItemRegistry.seed;
    }

    @Override
    public boolean canBePlanted(ItemStack stack, boolean forFermenting) {
        return Utils.isValidSeed(Utils.getItemDamage(stack));
    }

    @Override
    public ReplacementBlock getPlantedBlock(World world, int x, int y, int z, ItemStack stack) {
        return new ReplacementBlock(BlockRegistry.crop);
    }

    @Override
    public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack) {
        Block placed = world.getBlock(x, y - 1, z);

        return placed.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, new ItemSeed()) && Utils.isValidSeed(Utils.getItemDamage(stack)) && world.isAirBlock(x, y, z);
    }

    @Override
    public void prePlant(World world, int x, int y, int z, ItemStack stack) {

    }

    @Override
    public void postPlant(World world, int x, int y, int z, ItemStack stack) {
        Block crop = world.getBlock(x, y, z);

        if (crop instanceof BlockRCrop) {
            TileEntity cropTile = world.getTileEntity(x, y, z);

            if (cropTile != null && cropTile instanceof TileRCrop)
                ((TileRCrop) cropTile).setSeedName(SeedRegistry.getSeed(stack.getItemDamage()).getName());
        }
    }
}
