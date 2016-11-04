package tehnut.resourceful.crops2.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nullable;

/**
 * Helper class for those who are using a {@link FMLControlledNamespacedRegistry} to store objects that are used for
 * sub items in an ItemStack.
 *
 * @param <T> - The {@link IForgeRegistryEntry} type that the registry contains.
 */
public class ItemFMLRegistryWrapper<T extends IForgeRegistryEntry<T>> {

    private final FMLControlledNamespacedRegistry<T> registry;
    private final Item item;
    private final int invalidId;
    private String defaultPrefix = "minecraft";

    /**
     * @param registry  - The registry to get data from.
     * @param item      - The Item to create a stack for.
     * @param invalidId - The ID to use if the object is not found in the registry.
     */
    public ItemFMLRegistryWrapper(IForgeRegistry<T> registry, Item item, int invalidId) {
        if (!(registry instanceof FMLControlledNamespacedRegistry))
            throw new RuntimeException("The provided registry must be an FMLControlledNamespacedRegistry.");

        this.registry = (FMLControlledNamespacedRegistry<T>) registry;
        this.item = item;
        this.invalidId = invalidId;
    }

    /**
     * @see #getStack(ResourceLocation, int)
     */
    public ItemStack getStack(String name) {
        return getStack(name, 1);
    }

    /**
     * @see #getStack(ResourceLocation, int)
     */
    public ItemStack getStack(String name, int amount) {
        return getStack(name.contains(":") ? new ResourceLocation(name) : new ResourceLocation(getDefaultPrefix(), name), amount);
    }

    /**
     * @see #getStack(ResourceLocation, int)
     */
    public ItemStack getStack(ResourceLocation objectKey) {
        return getStack(objectKey, 1);
    }

    /**
     * Creates an ItemStack based on the provided {@link T}'s int ID in the registry.
     *
     * @param objectKey - The registry key used for the type.
     * @param amount    - The size of the ItemStack
     * @return the ItemStack for the Entry
     */
    public ItemStack getStack(ResourceLocation objectKey, int amount) {
        int meta = getRegistry().getId(objectKey);
        return new ItemStack(getItem(), amount, meta == -1 ? invalidId : meta);
    }

    /**
     * Creates an ItemStack based on the provided {@link T}'s int ID in the registry.
     *
     * @param customItem - A custom item.
     * @param objectKey  - The registry key used for the type.
     * @param amount     - The size of the ItemStack
     * @return the ItemStack for the Entry
     */
    public ItemStack getStack(Item customItem, ResourceLocation objectKey, int amount) {
        int meta = getRegistry().getId(objectKey);
        return new ItemStack(customItem, amount, meta == -1 ? invalidId : meta);
    }

    @Nullable
    public T getType(ItemStack stack) {
        return getRegistry().getObjectById(stack.getItemDamage());
    }

    /**
     * @return the default prefix for ResourceLocations
     */
    public String getDefaultPrefix() {
        return defaultPrefix;
    }

    /**
     * Sets a default {@link ResourceLocation} prefix to use when using Strings to lookup entries.
     * <p>
     * Allows lookups via {@code key_name} instead of {@code prefix:key_name}
     *
     * @param defaultPrefix - The default prefix to use for ResourceLocations.
     * @return self for chaining.
     */
    public ItemFMLRegistryWrapper<T> setDefaultPrefix(String defaultPrefix) {
        this.defaultPrefix = defaultPrefix;
        return this;
    }

    /**
     * @return the used registry.
     */
    public FMLControlledNamespacedRegistry<T> getRegistry() {
        return registry;
    }

    /**
     * @return the provided item.
     */
    public Item getItem() {
        return item;
    }
}
