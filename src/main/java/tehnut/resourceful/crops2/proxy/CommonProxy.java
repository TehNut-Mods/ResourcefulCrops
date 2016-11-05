package tehnut.resourceful.crops2.proxy;

import net.minecraftforge.common.MinecraftForge;
import tehnut.resourceful.crops2.core.ModObjects;
import tehnut.resourceful.crops2.util.EventHandler;

public class CommonProxy {

    private boolean mapped = false;

    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public void init() {

    }

    public void postInit() {

    }

    public void mapping() {
        if (mapped)
            return;
        ModObjects.mapping();
        mapped = true;
    }
}
