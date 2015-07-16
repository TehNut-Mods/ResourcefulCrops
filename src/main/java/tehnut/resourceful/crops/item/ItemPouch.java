package tehnut.resourceful.crops.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import tehnut.resourceful.crops.ModInformation;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.base.Seed;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.crops.registry.SeedRegistry;
import tehnut.resourceful.crops.tile.TileRCrop;
import tehnut.resourceful.crops.util.Utils;

import java.util.List;

public class ItemPouch extends Item implements IPlantable {

    public IIcon[] icons = new IIcon[2];

    public ItemPouch() {
        super();

        setUnlocalizedName(ModInformation.ID + ".pouch");
        setTextureName(ModInformation.TEXLOC + "pouch_base");
        setCreativeTab(ResourcefulCrops.tabResourcefulCrops);
        setHasSubtypes(true);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        boolean success = false;

        for (int posX = x - 1; posX <= x + 1; posX++) {
            for (int posZ = z - 1; posZ <= z + 1; posZ++) {
                Block placed = world.getBlock(posX, y, posZ);

                if (placed.canSustainPlant(world, posX, y, posZ, ForgeDirection.UP, this) && ForgeDirection.getOrientation(side) == ForgeDirection.UP && Utils.isValidSeed(Utils.getItemDamage(stack)) && world.isAirBlock(posX, y + 1, posZ)) {
                    world.setBlock(posX, y + 1, posZ, BlockRegistry.crop);
                    ((TileRCrop) world.getTileEntity(posX, y + 1, posZ)).setSeedName(SeedRegistry.getSeed(Utils.getItemDamage(stack)).getName());
                    if (!player.capabilities.isCreativeMode)
                        player.inventory.decrStackSize(player.inventory.currentItem, 1);

                    success = true;
                }
            }
        }

        return success;
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        for (Seed seed : SeedRegistry.getSeedList())
            list.add(new ItemStack(this, 1, SeedRegistry.getIndexOf(seed)));

        if (SeedRegistry.isEmpty())
            list.add(Utils.getInvalidSeed(this));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (Utils.isValidSeed(Utils.getItemDamage(stack)))
            return String.format(StatCollector.translateToLocal(getUnlocalizedName()), StatCollector.translateToLocal(SeedRegistry.getSeed(Utils.getItemDamage(stack)).getName()));
        else
            return String.format(StatCollector.translateToLocal(getUnlocalizedName()), StatCollector.translateToLocal("info.ResourcefulCrops.torn"));
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        if (!Utils.isValidSeed(Utils.getItemDamage(stack)))
            list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("info.ResourcefulCrops.warn"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister ir) {
        this.icons[0] = ir.registerIcon(ModInformation.TEXLOC + "pouch_base_color");
        this.icons[1] = ir.registerIcon(ModInformation.TEXLOC + "pouch_overlay");
    }

    public IIcon getIcon(ItemStack stack, int pass) {
        if (pass == 0)
            return icons[0];

        return icons[1];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if (pass == 1 && Utils.isValidSeed(Utils.getItemDamage(stack)))
            return SeedRegistry.getSeed(Utils.getItemDamage(stack)).getColor().getRGB();
        else
            return super.getColorFromItemStack(stack, pass);
    }

    @Override
    public int getRenderPasses(int metadata) {
        return requiresMultipleRenderPasses() ? 2 : 1;
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    // IPlantable

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Crop;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return BlockRegistry.crop;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }
}
