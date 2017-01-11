package tehnut.resourceful.crops.proxy;

import net.minecraftforge.common.MinecraftForge;
import tehnut.resourceful.crops.util.EventHandler;

public class CommonProxy {

    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public void init() {

    }

    public void postInit() {

    }
}
