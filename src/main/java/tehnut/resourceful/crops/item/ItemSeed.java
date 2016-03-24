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
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.EnumFacing;
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
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.crops.tile.TileRCrop;
import tehnut.resourceful.crops.util.Utils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@ModItem(name = "ItemSeed")
public class ItemSeed extends Item implements IPlantable, IMeshProvider {

    public ItemSeed() {
        super();

        setUnlocalizedName(ModInformation.ID + ".seed");
        setCreativeTab(ResourcefulCrops.tabResourcefulCrops);
        setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List<ItemStack> list) {
        for (int i = 0; i < SeedRegistry.getSize(); i++)
            if (Utils.isValidSeed(i))
                list.add(new ItemStack(this, 1, i));

        if (SeedRegistry.isEmpty())
            list.add(Utils.getInvalidSeed(this));
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

        Block placed = world.getBlockState(pos).getBlock();
        Seed seed = SeedRegistry.getSeed(stack.getItemDamage());

        if (seed == null)
            return EnumActionResult.FAIL;

        if (placed.canSustainPlant(world.getBlockState(pos), world, pos, EnumFacing.UP, this) && side == EnumFacing.UP && Utils.isValidSeed(Utils.getItemDamage(stack)) && world.isAirBlock(pos.offset(EnumFacing.UP))) {
            world.setBlockState(pos.offset(EnumFacing.UP), BlockHelper.getBlock(BlockRCrop.class).getDefaultState());
            ((TileRCrop) world.getTileEntity(pos.offset(EnumFacing.UP))).setSeedName(seed.getName());
            if (!player.capabilities.isCreativeMode)
                player.inventory.decrStackSize(player.inventory.currentItem, 1);

            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.FAIL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (Utils.isValidSeed(Utils.getItemDamage(stack)))
            return String.format(I18n.translateToLocal(getUnlocalizedName()), I18n.translateToLocal(SeedRegistry.getSeed(Utils.getItemDamage(stack)).getName()));
        else
            return String.format(I18n.translateToLocal(getUnlocalizedName()), I18n.translateToLocal("info.ResourcefulCrops.dead"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
        if (!Utils.isValidSeed(Utils.getItemDamage(stack)))
            list.add(TextFormatting.RED + I18n.translateToLocal("info.ResourcefulCrops.warn"));
        else
            list.add(String.format(I18n.translateToLocal("info.ResourcefulCrops.tier"), SeedRegistry.getSeed(Utils.getItemDamage(stack)).getTier()));
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
                return new ModelResourceLocation(new ResourceLocation(ModInformation.ID, "item/ItemSeed"), "type=normal");
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
