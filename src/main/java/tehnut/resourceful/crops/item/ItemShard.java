package tehnut.resourceful.crops.item;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.util.Utils;
import tehnut.resourceful.repack.tehnut.lib.annot.ModItem;
import tehnut.resourceful.repack.tehnut.lib.iface.IMeshProvider;

import java.util.Collections;
import java.util.List;

@ModItem(name = "ItemShard")
public class ItemShard extends Item implements IMeshProvider {

    public ItemShard() {
        super();

        setUnlocalizedName(ModInformation.ID + ".shard");
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

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (Utils.isValidSeed(Utils.getItemDamage(stack)))
            return String.format(I18n.translateToLocal(getUnlocalizedName()), I18n.translateToLocal(SeedRegistry.getSeed(Utils.getItemDamage(stack)).getName()));
        else
            return String.format(I18n.translateToLocal(getUnlocalizedName()), I18n.translateToLocal("info.ResourcefulCrops.broken"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
        if (!Utils.isValidSeed(Utils.getItemDamage(stack)))
            list.add(TextFormatting.RED + I18n.translateToLocal("info.ResourcefulCrops.warn"));
    }

    @Override
    public ItemMeshDefinition getMeshDefinition() {
        return new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(new ResourceLocation(ModInformation.ID, "item/ItemShard"), "type=normal");
            }
        };
    }

    @Override
    public List<String> getVariants() {
        return Collections.singletonList("type=normal");
    }
}
