package tehnut.resourceful.crops.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.item.*;
import tehnut.resourceful.crops.util.Utils;

public class RecipeRegistry {

    public static void registerItemRecipes() {

        // Seed/Shard/Pouch Recipes
        for (Seed seed : SeedRegistry.getSeedList()) {
            if (ConfigHandler.enableSeedCrafting) {
                GameRegistry.addRecipe(new ShapedOreRecipe(
                        new ItemStack(ItemRegistry.getItem(ItemSeed.class), seed.getAmount(), SeedRegistry.getIndexOf(seed)),
                        "IEI", "ESE", "IEI",
                        'I', Utils.parseItemStack(seed.getInput(), true) == null ? seed.getInput() : Utils.parseItemStack(seed.getInput(), true),
                        'E', new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, seed.getTier()),
                        'S', Items.wheat_seeds
                ));
            }

            if (ConfigHandler.enableShardCrafting) {
                if (seed.getOutput() != null) {
                    GameRegistry.addRecipe(new ShapedOreRecipe(
                                    seed.getOutput(),
                                    "SSS", "S S", "SSS",
                                    'S', new ItemStack(ItemRegistry.getItem(ItemShard.class), 1, SeedRegistry.getIndexOf(seed)))
                    );
                }


                if (seed.getSecondOutput() != null) {
                    GameRegistry.addRecipe(new ShapedOreRecipe(
                                    seed.getSecondOutput(),
                                    " S ", "SSS", " S ",
                                    'S', new ItemStack(ItemRegistry.getItem(ItemShard.class), 1, SeedRegistry.getIndexOf(seed)))
                    );
                }

                if (seed.getThirdOutput() != null) {
                    GameRegistry.addRecipe(new ShapedOreRecipe(
                                    seed.getThirdOutput(),
                                    "SS", "SS",
                                    'S', new ItemStack(ItemRegistry.getItem(ItemShard.class), 1, SeedRegistry.getIndexOf(seed)))
                    );
                }
            }

            if (ConfigHandler.enableSeedPouches && ConfigHandler.enableSeedCrafting) {
                GameRegistry.addRecipe(new ShapedOreRecipe(
                        new ItemStack(ItemRegistry.getItem(ItemPouch.class), 1, SeedRegistry.getIndexOf(seed)),
                        "SSS", "SSS", "SSS",
                        'S', new ItemStack(ItemRegistry.getItem(ItemSeed.class), 1, SeedRegistry.getIndexOf(seed))
                ));

                GameRegistry.addRecipe(new ShapelessOreRecipe(
                        new ItemStack(ItemRegistry.getItem(ItemSeed.class), 9, SeedRegistry.getIndexOf(seed)),
                        new ItemStack(ItemRegistry.getItem(ItemPouch.class), 1, SeedRegistry.getIndexOf(seed))
                ));
            }
        }

        // Stone Recipes
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 0),
                "MMM", "MGM", "MMM",
                'M', new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 0),
                'G', "gemDiamond"
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 0),
                "MMM", "MGM", "MMM",
                'M', new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 0),
                'G', "gemEmerald"
        ));

        for (Seed seed : SeedRegistry.getSeedList()) {
            if (seed.getTier() == 1) {
                GameRegistry.addRecipe(new ShapedOreRecipe(
                        new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 1),
                        "MMM", "MSM", "MMM",
                        'M', new ItemStack(ItemRegistry.getItem(ItemShard.class), 1, SeedRegistry.getIndexOf(seed)),
                        'S', new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 0)
                ));
            }

            if (seed.getTier() == 2) {
                GameRegistry.addRecipe(new ShapedOreRecipe(
                        new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 2),
                        "MMM", "MSM", "MMM",
                        'M', new ItemStack(ItemRegistry.getItem(ItemShard.class), 1, SeedRegistry.getIndexOf(seed)),
                        'S', new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 1)
                ));
            }

            if (seed.getTier() == 3) {
                GameRegistry.addRecipe(new ShapedOreRecipe(
                        new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 3),
                        "MMM", "MSM", "MMM",
                        'M', new ItemStack(ItemRegistry.getItem(ItemShard.class), 1, SeedRegistry.getIndexOf(seed)),
                        'S', new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 2)
                ));
            }

            if (seed.getTier() == 4) {
                GameRegistry.addRecipe(new ShapedOreRecipe(
                        new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 4),
                        "MMM", "MSM", "MMM",
                        'M', new ItemStack(ItemRegistry.getItem(ItemShard.class), 1, SeedRegistry.getIndexOf(seed)),
                        'S', new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 3)
                ));
            }
        }

        // Essence Recipes
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 1),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 0),
                'S', new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 0)
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 1),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 0),
                'S', new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 4)
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 2),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 1),
                'S', new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 1)
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 2),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 1),
                'S', new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 4)
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 3),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 2),
                'S', new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 2)
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 3),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 2),
                'S', new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 4)
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 4),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 3),
                'S', new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 3)
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 4),
                " M ", "MSM", " M ",
                'M', new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 3),
                'S', new ItemStack(ItemRegistry.getItem(ItemStone.class), 1, 4)
        ));

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 4, 0), new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 1)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 4, 1), new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 2)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 4, 2), new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 3)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 4, 3), new ItemStack(ItemRegistry.getItem(ItemMaterial.class), 1, 4)));
    }
}
