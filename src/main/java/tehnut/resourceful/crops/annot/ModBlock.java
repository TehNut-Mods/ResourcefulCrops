package tehnut.resourceful.crops.annot;

import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to automatically register Blocks with
 * {@link net.minecraftforge.fml.common.registry.GameRegistry#registerBlock(net.minecraft.block.Block, Class, String)}
 * <p/>
 * Uses {@code BlockClass.class.getSimpleName()} for {@link #name()} if one is not provided.
 * <p/>
 * Handles {@link ItemBlock} and {@link TileEntity} registration as well if values are
 * provided.
 * <p/>
 * Annotate any class that should be registered.
 * <p/>
 * Use {@link tehnut.resourceful.crops.registry.BlockRegistry#getBlock(Class)} or
 * {@link tehnut.resourceful.crops.api.ResourcefulAPI#getBlock(String)} to retrieve
 * a registered Block instance.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ModBlock {

    String name();

    Class<? extends TileEntity> tileEntity() default TileEntity.class;

    Class<? extends ItemBlock> itemBlock() default ItemBlock.class;
}
