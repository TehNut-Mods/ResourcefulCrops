package tehnut.resourceful.crops.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.repack.tehnut.lib.annot.Handler;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        for (ASMDataTable.ASMData data : ResourcefulCrops.instance.eventHandlers) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());
                boolean client = asmClass.getAnnotation(Handler.class).client();
                if (client)
                    MinecraftForge.EVENT_BUS.register(asmClass.newInstance());

            } catch (Exception e) {
                ResourcefulAPI.logger.fatal("Failed to register common EventHandlers");
            }
        }
    }

    @Override
    public void loadCommands() {

    }

    @Override
    public void loadRenders() {
        ItemRegistry.registerRenders();
        BlockRegistry.registerRenders();
    }

    @Override
    public void addChatMessage(String string) {
        addChatMessage(string, 1);
    }

    @Override
    public void addChatMessage(String string, int id) {
        Minecraft minecraft = Minecraft.getMinecraft();
        GuiNewChat chat = minecraft.ingameGUI.getChatGUI();

        chat.printChatMessageWithOptionalDeletion(new ChatComponentText(string), id);
    }
}
