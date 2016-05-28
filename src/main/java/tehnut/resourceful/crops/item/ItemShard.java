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
import tehnut.lib.annot.ModItem;
import tehnut.lib.iface.IMeshProvider;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.util.Utils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@ModItem(name = ResourcefulAPI.SHARD)
public class ItemShard extends Item implements IMeshProvider {

    public ItemShard() {
        super();

        setUnlocalizedName(ModInformation.ID + ".shard");
        setCreativeTab(ResourcefulCrops.tabResourcefulCrops);
        setHasSubtypes(true);
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
            return String.format(I18n.translateToLocal(getUnlocalizedName()), I18n.translateToLocal("info.ResourcefulCrops.broken"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
        if (!Utils.isValidSeed(Utils.getItemDamage(stack)))
            list.add(TextFormatting.RED + I18n.translateToLocal("info.ResourcefulCrops.warn"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getMeshDefinition() {
        return new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(new ResourceLocation(ModInformation.ID, "item/ItemShard"), "type=normal");
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
