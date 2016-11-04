package tehnut.resourceful.crops2.compat.jei;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import tehnut.resourceful.crops2.core.ModObjects;
import tehnut.resourceful.crops2.core.data.Seed;

import java.util.List;
import java.util.Set;

@JEIPlugin
public class ResourcefulCropsPlugin extends BlankModPlugin {

    public static IStackHelper stackHelper;

    @Override
    public void register(IModRegistry registry) {
        stackHelper = registry.getJeiHelpers().getStackHelper();
        registry.addRecipeHandlers(new ShapedListRecipeHandler());

        for (Seed seed : ModObjects.SEEDS.getValues()) {
            List<String> descriptions = Lists.newArrayList();
            if (seed.getGrowthRequirement().getMinLight() != 7)
                descriptions.add(I18n.translateToLocalFormatted("jei.resourcefulcrops.seed.info.minlight", MathHelper.clamp_int(seed.getGrowthRequirement().getMinLight(), 0, 15)));
            if (seed.getGrowthRequirement().getMaxLight() != 15)
                descriptions.add(I18n.translateToLocalFormatted("jei.resourcefulcrops.seed.info.maxlight", MathHelper.clamp_int(seed.getGrowthRequirement().getMaxLight(), 0, 15)));
            if (seed.getGrowthRequirement().getRequiredState() != null) {
                IBlockState state = seed.getGrowthRequirement().getRequiredState();
                ItemStack stateStack = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
                descriptions.add(I18n.translateToLocalFormatted("jei.resourcefulcrops.seed.info.requiredstate", stateStack.getDisplayName()));
            }
            if (!descriptions.isEmpty())
                registry.addDescription(ModObjects.SEED_WRAPPER.getStack(seed.getRegistryName()), descriptions.toArray(new String [descriptions.size()]));
        }
    }
}
