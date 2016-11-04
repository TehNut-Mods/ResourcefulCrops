package tehnut.resourceful.crops2.core;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.PersistentRegistryManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tehnut.resourceful.crops2.ResourcefulCrops2;
import tehnut.resourceful.crops2.block.BlockGaianiteOre;
import tehnut.resourceful.crops2.block.BlockResourcefulCrop;
import tehnut.resourceful.crops2.block.tile.TileSeedContainer;
import tehnut.resourceful.crops2.core.data.Output;
import tehnut.resourceful.crops2.core.data.Seed;
import tehnut.resourceful.crops2.core.recipe.ShapedListRecipe;
import tehnut.resourceful.crops2.item.*;
import tehnut.resourceful.crops2.util.ItemFMLRegistryWrapper;
import tehnut.resourceful.crops2.util.OreGenerator;

public class ModObjects {

    public static final Item SEED = new ItemResourcefulSeed();
    public static final Item POUCH = new ItemResourcefulPouch();
    public static final Item SHARD = new ItemResourceful("shard");
    public static final Item ESSENCE = new ItemEssence();
    public static final Item STONE = new ItemEarthStone();
    public static final Block CROP = new BlockResourcefulCrop();
    public static final Block ORE = new BlockGaianiteOre();

    public static final FMLControlledNamespacedRegistry<Seed> SEEDS = (FMLControlledNamespacedRegistry<Seed>) PersistentRegistryManager.createRegistry(
            new ResourceLocation(ResourcefulCrops2.MODID, "seeds"),
            Seed.class,
            null,
            0,
            Short.MAX_VALUE - 2,
            false,
            null,
            null,
            null
    );
    public static final ItemFMLRegistryWrapper<Seed> SEED_WRAPPER = new ItemFMLRegistryWrapper<Seed>(SEEDS, ModObjects.SEED, Short.MAX_VALUE - 1).setDefaultPrefix(ResourcefulCrops2.MODID);

    public static void preInit() {
        GameRegistry.register(SEED.setRegistryName("seed"));
        GameRegistry.register(POUCH.setRegistryName("pouch"));
        GameRegistry.register(SHARD.setRegistryName("shard"));
        GameRegistry.register(ESSENCE.setRegistryName("essence"));
        GameRegistry.register(STONE.setRegistryName("earth_stone"));
        GameRegistry.register(CROP.setRegistryName("crop"));
        GameRegistry.register(ORE.setRegistryName("ore"));
        GameRegistry.register(new ItemBlockMulti(ORE, "false", "true").setRegistryName(ORE.getRegistryName()));

        GameRegistry.registerTileEntity(TileSeedContainer.class, ResourcefulCrops2.MODID + ":seed_container");
    }

    // Register all the standard
    public static void init() {
        GameRegistry.addSmelting(new ItemStack(ORE), new ItemStack(ESSENCE), 0.5F);
        GameRegistry.addSmelting(new ItemStack(ORE, 1, 1), new ItemStack(ESSENCE), 0.5F);

        for (int i = 1; i <= 4; i++) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ESSENCE, 1, i), " E ", "ESE", " E ", 'E', new ItemStack(ESSENCE, 1, i - 1), 'S', new ItemStack(STONE, 1, i - 1)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ESSENCE, 1, i), " E ", "ESE", " E ", 'E', new ItemStack(ESSENCE, 1, i - 1), 'S', new ItemStack(STONE, 1, 4)));
        }

        GameRegistry.addRecipe(new ShapedOreRecipe(STONE, "EEE", "EDE", "EEE", 'E', ESSENCE, 'D', "gemDiamond"));

        GameRegistry.registerWorldGenerator(new OreGenerator(), 1);
    }

    // Handles seed specific recipes after their ID's have been mapped, otherwise we get de-sync when the original registry doesn't match the new one
    public static void mapping() {
        final ItemFMLRegistryWrapper<Seed> wrapper = SEED_WRAPPER;

        for (Seed seed : SEEDS) {
            if (ConfigHandler.crafting.enableSeedCrafting)
                GameRegistry.addRecipe(new ShapedListRecipe(wrapper.getStack(seed.getRegistryName(), seed.getCraftAmount()), "MEM", "ESE", "MEM", 'M', seed.getInputItems(), 'E', new ItemStack(ESSENCE, 1, seed.getTier() + 1), 'S', Items.WHEAT_SEEDS));
            if (ConfigHandler.crafting.enablePouchCrafting)
                GameRegistry.addRecipe(new ShapedOreRecipe(wrapper.getStack(POUCH, seed.getRegistryName(), 1), "SSS", "SSS", "SSS", 'S', wrapper.getStack(seed.getRegistryName())));
            GameRegistry.addRecipe(new ShapelessOreRecipe(wrapper.getStack(seed.getRegistryName(), 9), wrapper.getStack(POUCH, seed.getRegistryName(), 1)));

            if (ConfigHandler.crafting.enableShardCrafting) {
                for (Output output : seed.getOutputs()) {

                    String[] recipe = output.getShape().getRecipeFormat().split("#", 3);
                    if (recipe.length == 1 && !recipe[0].contains("S"))
                        recipe = Output.Shape.parseRecipe(recipe[0], output.getCustomFormat());
                    GameRegistry.addRecipe(new ShapedOreRecipe(output.getItem(), recipe, 'S', wrapper.getStack(SHARD, seed.getRegistryName(), 1)));
                }
            }

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(STONE, 1, seed.getTier() + 1), "MMM", "MSM", "MMM", 'M', wrapper.getStack(SHARD, seed.getRegistryName(), 1), 'S', new ItemStack(STONE, 1, seed.getTier())));
        }
    }
}
