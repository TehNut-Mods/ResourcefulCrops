package tehnut.resourceful.crops.core;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.block.BlockGaianiteOre;
import tehnut.resourceful.crops.block.BlockResourcefulCrop;
import tehnut.resourceful.crops.block.tile.TileSeedContainer;
import tehnut.resourceful.crops.core.data.Output;
import tehnut.resourceful.crops.core.data.Seed;
import tehnut.resourceful.crops.core.data.SeedStack;
import tehnut.resourceful.crops.core.recipe.RecipeHelper;
import tehnut.resourceful.crops.item.*;
import tehnut.resourceful.crops.util.OreGenerator;

import java.awt.Color;
import java.io.File;
import java.util.Collections;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(ResourcefulCrops.MODID)
public class RegistrarResourcefulCrops {

    // Each of these have defaults assigned to avoid invalid null inspections. They are replaced when ObjectHolders are filled.
    @GameRegistry.ObjectHolder("crop")
    public static final BlockResourcefulCrop CROP = new BlockResourcefulCrop();
    @GameRegistry.ObjectHolder("ore")
    public static final BlockGaianiteOre ORE = new BlockGaianiteOre();

    @GameRegistry.ObjectHolder("seed")
    public static final ItemResourcefulSeed SEED = new ItemResourcefulSeed();
    @GameRegistry.ObjectHolder("pouch")
    public static final ItemResourcefulPouch POUCH = new ItemResourcefulPouch();
    @GameRegistry.ObjectHolder("shard")
    public static final ItemResourceful SHARD = new ItemResourceful("");
    @GameRegistry.ObjectHolder("essence")
    public static final ItemEssence ESSENCE = new ItemEssence();
    @GameRegistry.ObjectHolder("earth_stone")
    public static final ItemEarthStone EARTH_STONE = new ItemEarthStone();

    public static ResourceLocation SEED_DEFAULT = new ResourceLocation(ResourcefulCrops.MODID, "null");
    public static IForgeRegistry<Seed> SEEDS = null;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockResourcefulCrop().setRegistryName("crop"));
        event.getRegistry().register(new BlockGaianiteOre().setRegistryName("ore"));

        GameRegistry.registerTileEntity(TileSeedContainer.class, ResourcefulCrops.MODID + ":seed_container");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlockMulti(ORE, "false", "true").setRegistryName(ORE.getRegistryName()));

        event.getRegistry().register(new ItemResourcefulSeed().setRegistryName("seed"));
        event.getRegistry().register(new ItemResourcefulPouch().setRegistryName("pouch"));
        event.getRegistry().register(new ItemResourceful("shard").setRegistryName("shard"));
        event.getRegistry().register(new ItemEssence().setRegistryName("essence"));
        event.getRegistry().register(new ItemEarthStone().setRegistryName("earth_stone"));
    }
    
    @SubscribeEvent
    public static void registerSeeds(RegistryEvent.Register<Seed> event) {
        event.getRegistry().register(new Seed("null", 0, 0, Color.BLACK, Collections.emptyList(), new Output[] {}, null).setRegistryName(SEED_DEFAULT));
        SeedLoader.init(new File(ResourcefulCrops.configDir, "seeds"), event.getRegistry());
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        for (int i = 1; i <= 4; i++) {
            event.getRegistry().register(RecipeHelper.newShaped(new ItemStack(ESSENCE, 1, i), " E ", "ESE", " E ", 'E', new ItemStack(ESSENCE, 1, i - 1), 'S', new ItemStack(EARTH_STONE, 1, i - 1)).setRegistryName("essence_tier" + i));
            event.getRegistry().register(RecipeHelper.newShaped(new ItemStack(ESSENCE, 1, i), " E ", "ESE", " E ", 'E', new ItemStack(ESSENCE, 1, i - 1), 'S', new ItemStack(EARTH_STONE, 1, 4)).setRegistryName("master_essence_tier" + i));
        }

        Multimap<Integer, SeedStack> tiers = ArrayListMultimap.create();

        for (Seed seed : SEEDS) {
            if (SEED_DEFAULT.equals(seed.getRegistryName()))
                continue;

            if (ConfigHandler.crafting.enableSeedCrafting)
                event.getRegistry().register(RecipeHelper.newShaped(new SeedStack(SEED, seed, seed.getCraftAmount()), "MEM", "ESE", "MEM", 'M', seed.getInputItems(), 'E', new ItemStack(ESSENCE, 1, seed.getTier() + 1), 'S', Items.WHEAT_SEEDS).setRegistryName( "seed_" + seed.getRegistryName().getResourcePath()));
            if (ConfigHandler.crafting.enablePouchCrafting)
                event.getRegistry().register(RecipeHelper.newShaped(new SeedStack(POUCH, seed), "SSS", "SSS", "SSS", 'S', new SeedStack(SEED, seed.getRegistryName())).setRegistryName("pouch_" + seed.getRegistryName().getResourcePath()));
            event.getRegistry().register(RecipeHelper.newShaped(new SeedStack(SEED, seed, 9), "P", 'P', new SeedStack(POUCH, seed)).setRegistryName("unpouch_" + seed.getRegistryName().getResourcePath()));

            if (ConfigHandler.crafting.enableShardCrafting) {
                for (Output output : seed.getOutputs()) {
                    String[] recipe;
                    if (output.getShape() == Output.Shape.CUSTOM)
                        recipe = Output.Shape.parseRecipe(output.getShape().name(), output.getCustomFormat());
                    else if (output.getShape() == Output.Shape.DEFAULT)
                        recipe = Output.Shape.parseRecipe(output.getShape().getRecipeFormat(), output.getCustomFormat());
                    else
                        recipe = output.getShape().getRecipeFormat().split("#", 3);
                    event.getRegistry().register(RecipeHelper.newShaped(output.getItem(), recipe, 'S', new SeedStack(SHARD, seed)).setRegistryName("shard_" + seed.getRegistryName().getResourcePath() + "_" + output.getShape()));
                }
            }
            // Collect seeds based on Tier for stone upgrades
            tiers.put(seed.getTier(), new SeedStack(SHARD, seed));
        }

        event.getRegistry().register(RecipeHelper.newShaped(new ItemStack(EARTH_STONE), "EEE", "EDE", "EEE", 'E', new ItemStack(ESSENCE), 'D', "gemDiamond").setRegistryName("stone_mundane"));
        if (!tiers.get(0).isEmpty())
            event.getRegistry().register(RecipeHelper.newShaped(new ItemStack(EARTH_STONE, 1, 1), "MMM", "MSM", "MMM", 'M', tiers.get(0), 'S', new ItemStack(EARTH_STONE, 1, 0)).setRegistryName("stone_magical"));
        if (!tiers.get(1).isEmpty())
            event.getRegistry().register(RecipeHelper.newShaped(new ItemStack(EARTH_STONE, 1, 2), "MMM", "MSM", "MMM", 'M', tiers.get(1), 'S', new ItemStack(EARTH_STONE, 1, 1)).setRegistryName("stone_infused"));
        if (!tiers.get(2).isEmpty())
            event.getRegistry().register(RecipeHelper.newShaped(new ItemStack(EARTH_STONE, 1, 3), "MMM", "MSM", "MMM", 'M', tiers.get(2), 'S', new ItemStack(EARTH_STONE, 1, 2)).setRegistryName("stone_arcane"));
        if (!tiers.get(3).isEmpty())
            event.getRegistry().register(RecipeHelper.newShaped(new ItemStack(EARTH_STONE, 1, 4), "MMM", "MSM", "MMM", 'M', tiers.get(3), 'S', new ItemStack(EARTH_STONE, 1, 3)).setRegistryName("stone_true"));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        for (int i = 0; i < ItemEarthStone.STONES.length; i++)
            ModelLoader.setCustomModelResourceLocation(EARTH_STONE, i, new ModelResourceLocation(EARTH_STONE.getRegistryName(), "inventory"));

        ModelLoader.setCustomModelResourceLocation(SEED, 0, new ModelResourceLocation(SEED.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(POUCH, 0, new ModelResourceLocation(POUCH.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(SHARD, 0, new ModelResourceLocation(SHARD.getRegistryName(), "inventory"));

        ModelLoader.setCustomModelResourceLocation(ESSENCE, 0, new ModelResourceLocation(ESSENCE.getRegistryName(), "type=normal"));
        ModelLoader.setCustomModelResourceLocation(ESSENCE, 1, new ModelResourceLocation(ESSENCE.getRegistryName(), "type=mundane"));
        ModelLoader.setCustomModelResourceLocation(ESSENCE, 2, new ModelResourceLocation(ESSENCE.getRegistryName(), "type=magical"));
        ModelLoader.setCustomModelResourceLocation(ESSENCE, 3, new ModelResourceLocation(ESSENCE.getRegistryName(), "type=infused"));
        ModelLoader.setCustomModelResourceLocation(ESSENCE, 4, new ModelResourceLocation(ESSENCE.getRegistryName(), "type=arcane"));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ORE), 0, new ModelResourceLocation(ORE.getRegistryName(), "nether=false"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ORE), 1, new ModelResourceLocation(ORE.getRegistryName(), "nether=true"));
    }

    @SubscribeEvent
    public static void createRegistries(RegistryEvent.NewRegistry event) {
        SEEDS = new RegistryBuilder<Seed>()
                .setName(new ResourceLocation("_" + ResourcefulCrops.MODID, "seeds")) // _ so we fire before minecraft:recipes
                .setType(Seed.class)
                .setDefaultKey(SEED_DEFAULT)
                .setIDRange(0, Short.MAX_VALUE - 2)
                .create();
    }
    
    public static void registerOthers() {
        GameRegistry.registerWorldGenerator(new OreGenerator(), 1);

        GameRegistry.addSmelting(new ItemStack(ORE), new ItemStack(ESSENCE), 0.5F);
        GameRegistry.addSmelting(new ItemStack(ORE, 1, 1), new ItemStack(ESSENCE), 0.5F);
    }
}
