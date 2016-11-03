package tehnut.resourceful.crops.registry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tehnut.lib.util.helper.ItemHelper;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.api.base.Output;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.item.*;
import tehnut.resourceful.crops.util.Utils;

public class RecipeRegistry {

    public static void registerItemRecipes() {

        // Seed/Shard/Pouch Recipes
        for (Seed seed : ResourcefulAPI.SEEDS.getValues()) {
            if (ConfigHandler.enableSeedCrafting) {
                GameRegistry.addRecipe(new ShapedOreRecipe(
                        new ItemStack(ItemHelper.getItem(ItemSeed.class), seed.getAmount(), ResourcefulAPI.SEEDS.getId(seed)),
                        "IEI", "ESE", "IEI",
                        'I', Utils.parseItemStack(seed.getInput(), true) == null ? seed.getInput() : Utils.parseItemStack(seed.getInput(), true),
                        'E', new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, seed.getTier()),
                        'S', Items.WHEAT_SEEDS
                ));
            }

            if (ConfigHandler.enableShardCrafting) {
                if (seed.getOutput() != null) {
                    for (Output output : seed.getOutput()) {
                        try {
                            GameRegistry.addRecipe(new ShapedOreRecipe(
                                    output.getOutputStack(),
                                    output.getRecipe(),
                                    'S', new ItemStack(ItemHelper.getItem(ItemShard.class), 1, ResourcefulAPI.SEEDS.getId(seed))));
                        } catch (NullPointerException e) {
                            ResourcefulAPI.logger.error("Error registering recipes for output: " + output, e);
                        }
                    }
                }
            }

            if (ConfigHandler.enableSeedPouches && ConfigHandler.enableSeedCrafting) {
                GameRegistry.addRecipe(new ShapedOreRecipe(
                        new ItemStack(ItemHelper.getItem(ItemPouch.class), 1, ResourcefulAPI.SEEDS.getId(seed)),
                        "SSS", "SSS", "SSS",
                        'S', new ItemStack(ItemHelper.getItem(ItemSeed.class), 1, ResourcefulAPI.SEEDS.getId(seed))
                ));

                GameRegistry.addRecipe(new ShapelessOreRecipe(
                        new ItemStack(ItemHelper.getItem(ItemSeed.class), 9, ResourcefulAPI.SEEDS.getId(seed)),
                        new ItemStack(ItemHelper.getItem(ItemPouch.class), 1, ResourcefulAPI.SEEDS.getId(seed))
                ));
            }
        }

        // Stone Recipes
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 0),
                "MMM", "MGM", "MMM",
                'M', new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 0),
                'G', "gemDiamond"
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 0),
                "MMM", "MGM", "MMM",
                'M', new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 0),
                'G', "gemEmerald"
        ));

        for (Seed seed : ResourcefulAPI.SEEDS.getValues()) {
            if (seed.getTier() == 1) {
                GameRegistry.addRecipe(new ShapedOreRecipe(
                        new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 1),
                        "MMM", "MSM", "MMM",
                        'M', new ItemStack(ItemHelper.getItem(ItemShard.class), 1, ResourcefulAPI.SEEDS.getId(seed)),
                        'S', new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 0)
                ));
            }

            if (seed.getTier() == 2) {
                GameRegistry.addRecipe(new ShapedOreRecipe(
                        new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 2),
                        "MMM", "MSM", "MMM",
                        'M', new ItemStack(ItemHelper.getItem(ItemShard.class), 1, ResourcefulAPI.SEEDS.getId(seed)),
                        'S', new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 1)
                ));
            }

            if (seed.getTier() == 3) {
                GameRegistry.addRecipe(new ShapedOreRecipe(
                        new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 3),
                        "MMM", "MSM", "MMM",
                        'M', new ItemStack(ItemHelper.getItem(ItemShard.class), 1, ResourcefulAPI.SEEDS.getId(seed)),
                        'S', new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 2)
                ));
            }

            if (seed.getTier() == 4) {
                GameRegistry.addRecipe(new ShapedOreRecipe(
                        new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 4),
                        "MMM", "MSM", "MMM",
                        'M', new ItemStack(ItemHelper.getItem(ItemShard.class), 1, ResourcefulAPI.SEEDS.getId(seed)),
                        'S', new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 3)
                ));
            }
        }

        // Essence Recipes
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 1),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 0),
                'S', new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 0)
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 1),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 0),
                'S', new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 4)
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 2),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 1),
                'S', new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 1)
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 2),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 1),
                'S', new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 4)
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 3),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 2),
                'S', new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 2)
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 3),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 2),
                'S', new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 4)
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 4),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 3),
                'S', new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 3)
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 4),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 3),
                'S', new ItemStack(ItemHelper.getItem(ItemStone.class), 1, 4)
        ));

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHelper.getItem(ItemMaterial.class), 4, 0), new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 1)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHelper.getItem(ItemMaterial.class), 4, 1), new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 2)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHelper.getItem(ItemMaterial.class), 4, 2), new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 3)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHelper.getItem(ItemMaterial.class), 4, 3), new ItemStack(ItemHelper.getItem(ItemMaterial.class), 1, 4)));
    }
}
