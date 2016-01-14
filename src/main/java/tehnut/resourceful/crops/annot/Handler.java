package tehnut.resourceful.crops.annot;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to automatically handle registration of EventHandlers for the
 * {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS}.
 * <p/>
 * Annotate any class that should be subscribed.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler {

    boolean client() default false;
}