package tehnut.resourceful.crops.proxies;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.client.render.RenderRCrop;

public class ClientProxy extends CommonProxy {

    @Override
    public void load() {

        ConfigHandler.enableFancyRender = Minecraft.getMinecraft().gameSettings.fancyGraphics;

        ResourcefulCrops.renderIDCrop = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RenderRCrop());
    }
}
