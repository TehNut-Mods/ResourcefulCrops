package tehnut.resourceful.crops.util;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.api.util.BlockStack;

import java.util.regex.Pattern;

public class Utils {

    private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");

    /**
     * Provides an invalid version of the given item. Intended to be
     * used as a fallback for if the entry is invalid and returning
     * null would break things.
     *
     * @param item  - Item to give an invalid version of
     * @return      - Invalid version of the given item
     */
    public static ItemStack getInvalidSeed(Item item) {
        return new ItemStack(item, 1, Short.MAX_VALUE);
    }

    /**
     * Provides the damage value of the given ItemStack.
     * If I ever decide to stop determining things based on
     * damage value, this makes find/replace that much easier.
     * Otherwise it's just a redundant method that's longer than
     * {@code stack.getItemDamage()}
     *
     * @param stack - ItemStack to get the metadata of.
     * @return      - Metadata of the given ItemStack
     */
    public static int getItemDamage(ItemStack stack) {
        return stack.getItemDamage();
    }

    /**
     * Determines if the given seed is valid or not based on
     * the index of it.
     *
     * @param seedIndex - Index of the seed to check.
     * @return          - Whether or not the seed is valid.
     */
    public static boolean isValidSeed(int seedIndex) {
        return SeedRegistry.getSeed(seedIndex) != null;
    }

    /**
     * Determines if the given seed is valid or not based on
     * the registered name of it.
     *
     * @param seedName - Name of the seed to check.
     * @return         - Whether or not the seed is valid.
     */
    public static boolean isValidSeed(String seedName) {
        return SeedRegistry.getSeed(seedName) != null;
    }

    /**
     * Determines if the given seed is valid or not.
     *
     * @param seed - Seed to check.
     * @return     - Whether or not the seed is valid.
     */
    public static boolean isValidSeed(Seed seed) {
        return SeedRegistry.getSeedList().contains(seed);
    }

    /**
     * Checks if the provided EntityPlayer is instanceof FakePlayer or if the name matches
     * known non-standard FakePlayer names.
     *
     * @param player - Player to check
     * @return - Whether the provided EntityPlayer is a FakePlayer.
     */
    public static boolean isFakePlayer(EntityPlayer player) {
        return player instanceof FakePlayer || FAKE_PLAYER_PATTERN.matcher(player.getGameProfile().getName()).matches();
    }

    /**
     * Causes a break animation at the given coordinates.
     * No reason to use this except for possible future proofing.
     *
     * @param world      - The world object
     * @param x          - X coordinate to play at
     * @param y          - Y coordinate to play at
     * @param z          - Z coordinate to play at
     * @param blockStack - {@link BlockStack} to get the particle texture from
     */
    public static void playBlockBreakAnim(World world, int x, int y, int z, BlockStack blockStack) {
        world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(blockStack.getBlock()) + (blockStack.getMeta() << 12));
    }
}
