package tehnut.resourceful.crops.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.client.render.RenderRCrop;
import tehnut.resourceful.crops.command.CommandCreateSeed;
import tehnut.resourceful.crops.command.CommandPrintSeed;

public class ClientProxy extends CommonProxy {

    @Override
    public void load() {

        ConfigHandler.enableFancyRender = Minecraft.getMinecraft().gameSettings.fancyGraphics;

        ResourcefulCrops.renderIDCrop = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RenderRCrop());
    }

    @Override
    public void loadCommands() {
        ClientCommandHandler.instance.registerCommand(new CommandCreateSeed());
        ClientCommandHandler.instance.registerCommand(new CommandPrintSeed());
    }
}
