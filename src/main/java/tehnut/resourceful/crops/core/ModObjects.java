package tehnut.resourceful.crops.core;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.RegistryBuilder;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.block.BlockGaianiteOre;
import tehnut.resourceful.crops.block.BlockResourcefulCrop;
import tehnut.resourceful.crops.block.tile.TileSeedContainer;
import tehnut.resourceful.crops.core.data.Output;
import tehnut.resourceful.crops.core.data.Seed;
import tehnut.resourceful.crops.core.data.SeedStack;
import tehnut.resourceful.crops.core.recipe.ShapedSeedRecipe;
import tehnut.resourceful.crops.item.*;
import tehnut.resourceful.crops.util.OreGenerator;

public class ModObjects {

    public static final ItemResourceful SEED = new ItemResourcefulSeed();
    public static final ItemResourceful POUCH = new ItemResourcefulPouch();
    public static final ItemResourceful SHARD = new ItemResourceful("shard");
    public static final Item ESSENCE = new ItemEssence();
    public static final Item STONE = new ItemEarthStone();
    public static final Block CROP = new BlockResourcefulCrop();
    public static final Block ORE = new BlockGaianiteOre();

    public static final FMLControlledNamespacedRegistry<Seed> SEEDS = (FMLControlledNamespacedRegistry<Seed>) new RegistryBuilder<Seed>()
            .setName(new ResourceLocation(ResourcefulCrops.MODID, "seeds"))
            .setType(Seed.class)
            .setIDRange(0, Short.MAX_VALUE - 2)
            .create();

    public static void preInit() {
        GameRegistry.register(SEED.setRegistryName("seed"));
        GameRegistry.register(POUCH.setRegistryName("pouch"));
        GameRegistry.register(SHARD.setRegistryName("shard"));
        GameRegistry.register(ESSENCE.setRegistryName("essence"));
        GameRegistry.register(STONE.setRegistryName("earth_stone"));
        GameRegistry.register(CROP.setRegistryName("crop"));
        GameRegistry.register(ORE.setRegistryName("ore"));
        GameRegistry.register(new ItemBlockMulti(ORE, "false", "true").setRegistryName(ORE.getRegistryName()));

        GameRegistry.registerTileEntity(TileSeedContainer.class, ResourcefulCrops.MODID + ":seed_container");
    }

    // Register all the standard
    public static void init() {
        GameRegistry.registerWorldGenerator(new OreGenerator(), 1);
        
        GameRegistry.addSmelting(new ItemStack(ORE), new ItemStack(ESSENCE), 0.5F);
        GameRegistry.addSmelting(new ItemStack(ORE, 1, 1), new ItemStack(ESSENCE), 0.5F);

        for (int i = 1; i <= 4; i++) {
            GameRegistry.register(new ShapedSeedRecipe(new ItemStack(ESSENCE, 1, i), " E ", "ESE", " E ", 'E', new ItemStack(ESSENCE, 1, i - 1), 'S', new ItemStack(STONE, 1, i - 1)).setRegistryName("essence_tier" + i));
            GameRegistry.register(new ShapedSeedRecipe(new ItemStack(ESSENCE, 1, i), " E ", "ESE", " E ", 'E', new ItemStack(ESSENCE, 1, i - 1), 'S', new ItemStack(STONE, 1, 4)).setRegistryName("master_essence_tier" + i));
        }

        Multimap<Integer, SeedStack> tiers = ArrayListMultimap.create();

        for (Seed seed : SEEDS) {
            if (ConfigHandler.crafting.enableSeedCrafting)
                GameRegistry.register(new ShapedSeedRecipe(new SeedStack(SEED, seed, seed.getCraftAmount()), "MEM", "ESE", "MEM", 'M', seed.getInputItems(), 'E', new ItemStack(ESSENCE, 1, seed.getTier() + 1), 'S', Items.WHEAT_SEEDS).setRegistryName( "seed_" + seed.getRegistryName().getResourcePath()));
            if (ConfigHandler.crafting.enablePouchCrafting)
                GameRegistry.register(new ShapedSeedRecipe(new SeedStack(POUCH, seed), "SSS", "SSS", "SSS", 'S', new SeedStack(SEED, seed.getRegistryName())).setRegistryName("pouch_" + seed.getRegistryName().getResourcePath()));
            GameRegistry.register(new ShapedSeedRecipe(new SeedStack(SEED, seed, 9), "P", 'P', new SeedStack(POUCH, seed)).setRegistryName("unpouch_" + seed.getRegistryName().getResourcePath()));

            if (ConfigHandler.crafting.enableShardCrafting) {
                for (Output output : seed.getOutputs()) {
                    String[] recipe;
                    if (output.getShape() == Output.Shape.CUSTOM)
                        recipe = Output.Shape.parseRecipe(output.getShape().name(), output.getCustomFormat());
                    else if (output.getShape() == Output.Shape.DEFAULT)
                        recipe = Output.Shape.parseRecipe(output.getShape().getRecipeFormat(), output.getCustomFormat());
                    else
                        recipe = output.getShape().getRecipeFormat().split("#", 3);
                    GameRegistry.register(new ShapedSeedRecipe(output.getItem(), recipe, 'S', new SeedStack(SHARD, seed)).setRegistryName("shard_" + seed.getRegistryName().getResourcePath() + "_" + output.getShape()));
                }
            }
            // Collect seeds based on Tier for stone upgrades
            tiers.put(seed.getTier(), new SeedStack(SHARD, seed));
        }

        GameRegistry.register(new ShapedSeedRecipe(new ItemStack(STONE), "EEE", "EDE", "EEE", 'E', ESSENCE, 'D', "gemDiamond").setRegistryName("stone_mundane"));
        if (!tiers.get(0).isEmpty())
            GameRegistry.register(new ShapedSeedRecipe(new ItemStack(STONE, 1, 1), "MMM", "MSM", "MMM", 'M', tiers.get(0), 'S', new ItemStack(STONE, 1, 0)).setRegistryName("stone_magical"));
        if (!tiers.get(1).isEmpty())
            GameRegistry.register(new ShapedSeedRecipe(new ItemStack(STONE, 1, 2), "MMM", "MSM", "MMM", 'M', tiers.get(1), 'S', new ItemStack(STONE, 1, 1)).setRegistryName("stone_infused"));
        if (!tiers.get(2).isEmpty())
            GameRegistry.register(new ShapedSeedRecipe(new ItemStack(STONE, 1, 3), "MMM", "MSM", "MMM", 'M', tiers.get(2), 'S', new ItemStack(STONE, 1, 2)).setRegistryName("stone_arcane"));
        if (!tiers.get(3).isEmpty())
            GameRegistry.register(new ShapedSeedRecipe(new ItemStack(STONE, 1, 4), "MMM", "MSM", "MMM", 'M', tiers.get(3), 'S', new ItemStack(STONE, 1, 3)).setRegistryName("stone_true"));
    }
}
