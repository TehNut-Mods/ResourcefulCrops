package tehnut.resourceful.crops.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.lib.annot.ModItem;
import tehnut.lib.iface.IMeshProvider;
import tehnut.lib.util.helper.BlockHelper;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.crops.tile.TileRCrop;
import tehnut.resourceful.crops.util.Utils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@ModItem(name = ResourcefulAPI.POUCH)
public class ItemPouch extends Item implements IPlantable, IMeshProvider {

    public ItemPouch() {
        super();

        setUnlocalizedName(ModInformation.ID + ".pouch");
        setCreativeTab(ResourcefulCrops.tabResourcefulCrops);
        setHasSubtypes(true);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

        EnumActionResult ret = EnumActionResult.FAIL;

        for (int posX = pos.getX() - 1; posX <= pos.getX() + 1; posX++) {
            for (int posZ = pos.getZ() - 1; posZ <= pos.getZ() + 1; posZ++) {

                Block placed = world.getBlockState(new BlockPos(posX, pos.getY(), posZ)).getBlock();
                BlockPos placeAt = new BlockPos(posX, pos.getY(), posZ);
                if (placed.canSustainPlant(world.getBlockState(placeAt), world, placeAt, EnumFacing.UP, this) && side == EnumFacing.UP && Utils.isValidSeed(Utils.getItemDamage(stack)) && world.isAirBlock(new BlockPos(posX, pos.getY() + 1, posZ))) {
                    world.setBlockState(new BlockPos(posX, pos.getY() + 1, posZ), BlockHelper.getBlock(BlockRCrop.class).getDefaultState());
                    ((TileRCrop) world.getTileEntity(new BlockPos(posX, pos.getY() + 1, posZ))).setSeedName(ResourcefulAPI.SEEDS.getRaw(Utils.getItemDamage(stack)).getRegistryName());
                    if (!player.capabilities.isCreativeMode)
                        player.inventory.decrStackSize(player.inventory.currentItem, 1);

                    ret = EnumActionResult.SUCCESS;
                }
            }
        }

        return ret;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List<ItemStack> list) {
        for (int i = 0; i < ResourcefulAPI.SEEDS.getValues().size(); i++)
            if (Utils.isValidSeed(i))
                list.add(new ItemStack(this, 1, i));

        if (ResourcefulAPI.SEEDS.getValues().isEmpty())
            list.add(Utils.getInvalidSeed(this));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (Utils.isValidSeed(Utils.getItemDamage(stack)))
            return String.format(I18n.translateToLocal(getUnlocalizedName()), I18n.translateToLocal(ResourcefulAPI.SEEDS.getRaw(Utils.getItemDamage(stack)).getName()));
        else
            return String.format(I18n.translateToLocal(getUnlocalizedName()), I18n.translateToLocal("info.ResourcefulCrops.torn"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
        if (!Utils.isValidSeed(Utils.getItemDamage(stack)))
            list.add(TextFormatting.RED + I18n.translateToLocal("info.ResourcefulCrops.warn"));
    }

    // IPlantable

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return BlockHelper.getBlock(BlockRCrop.class).getDefaultState();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getMeshDefinition() {
        return new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(new ResourceLocation(ModInformation.ID, "item/ItemPouch"), "type=normal");
            }
        };
    }

    @Nullable
    @Override
    public ResourceLocation getCustomLocation() {
        return null;
    }

    @Override
    public List<String> getVariants() {
        return Collections.singletonList("type=normal");
    }
}
