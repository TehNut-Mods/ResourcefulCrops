package tehnut.resourceful.crops2.proxy;

import net.minecraftforge.common.MinecraftForge;
import tehnut.resourceful.crops2.util.EventHandler;

public class CommonProxy {

    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public void init() {

    }

    public void postInit() {

    }
}
