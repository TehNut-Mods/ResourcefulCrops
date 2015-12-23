package tehnut.resourceful.crops.annot;

import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModBlock {

    String name() default "";

    Class<? extends TileEntity> tileEntity() default TileEntity.class;

    Class<? extends ItemBlock> itemBlock() default ItemBlock.class;
}
