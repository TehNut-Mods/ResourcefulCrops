package tehnut.resourceful.crops.annot;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to automatically register Items with
 * {@link net.minecraftforge.fml.common.registry.GameRegistry#registerItem(net.minecraft.item.Item, String)}
 * <p/>
 * Uses {@code ItemClass.class.getSimpleName()} for {@link #name()} if one is not provided.
 * <p/>
 * Annotate any class that should be registered.
 * <p/>
 * Use {@link tehnut.resourceful.crops.registry.ItemRegistry#getItem(Class)} or
 * {@link tehnut.resourceful.crops.api.ResourcefulAPI#getItem(String)} to retrieve
 * a registered Item instance.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ModItem {

    String name() default "";
}
