package tehnut.resourceful.crops2.proxy;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.resourceful.crops2.block.tile.TileSeedContainer;
import tehnut.resourceful.crops2.core.data.Seed;
import tehnut.resourceful.crops2.core.ModObjects;
import tehnut.resourceful.crops2.item.ItemResourceful;
import tehnut.resourceful.crops2.util.Util;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();

        final ItemMeshDefinition ALL_FOR_ONE = new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(stack.getItem().getRegistryName(), "inventory");
            }
        };

        ModelLoader.setCustomMeshDefinition(ModObjects.SEED, ALL_FOR_ONE);
        ModelLoader.setCustomMeshDefinition(ModObjects.POUCH, ALL_FOR_ONE);
        ModelLoader.setCustomMeshDefinition(ModObjects.SHARD, ALL_FOR_ONE);
        ModelLoader.setCustomMeshDefinition(ModObjects.STONE, ALL_FOR_ONE);

        ModelLoader.setCustomModelResourceLocation(ModObjects.ESSENCE, 0, new ModelResourceLocation(ModObjects.ESSENCE.getRegistryName(), "type=normal"));
        ModelLoader.setCustomModelResourceLocation(ModObjects.ESSENCE, 1, new ModelResourceLocation(ModObjects.ESSENCE.getRegistryName(), "type=mundane"));
        ModelLoader.setCustomModelResourceLocation(ModObjects.ESSENCE, 2, new ModelResourceLocation(ModObjects.ESSENCE.getRegistryName(), "type=magical"));
        ModelLoader.setCustomModelResourceLocation(ModObjects.ESSENCE, 3, new ModelResourceLocation(ModObjects.ESSENCE.getRegistryName(), "type=infused"));
        ModelLoader.setCustomModelResourceLocation(ModObjects.ESSENCE, 4, new ModelResourceLocation(ModObjects.ESSENCE.getRegistryName(), "type=arcane"));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModObjects.ORE), 0, new ModelResourceLocation(ModObjects.ORE.getRegistryName(), "nether=false"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModObjects.ORE), 1, new ModelResourceLocation(ModObjects.ORE.getRegistryName(), "nether=true"));
    }

    @Override
    public void init() {
        super.init();

        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
            @Override
            public int getColorFromItemstack(ItemStack stack, int tintIndex) {
                Seed seed = ((ItemResourceful) stack.getItem()).getSeed(stack);
                if (seed == null || (stack.getItem() == ModObjects.POUCH && tintIndex != 1))
                    return -1;

                return seed.getColor().getRGB();
            }
        }, ModObjects.SEED, ModObjects.POUCH, ModObjects.SHARD);

        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess blockAccess, @Nullable BlockPos pos, int tintIndex) {
                if (blockAccess == null || pos == null)
                    return -1;
                TileSeedContainer seedContainer = Util.getSeedContainer(blockAccess, pos);
                if (seedContainer == null)
                    return -1;

                Seed seed = ModObjects.SEEDS.getValue(seedContainer.getSeedKey());
                if (seed == null)
                    return -1;

                return seed.getColor().getRGB();
            }
        }, ModObjects.CROP);
    }

    @Override
    public void postInit() {
        super.postInit();
    }
}
