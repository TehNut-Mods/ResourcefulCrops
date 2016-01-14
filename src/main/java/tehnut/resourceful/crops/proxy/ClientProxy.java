package tehnut.resourceful.crops.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.annot.Handler;
import tehnut.resourceful.crops.command.CommandCreateSeed;
import tehnut.resourceful.crops.command.CommandPrintSeed;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.crops.util.helper.LogHelper;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();

        for (ASMDataTable.ASMData data : ResourcefulCrops.instance.eventHandlers) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());
                boolean client = asmClass.getAnnotation(Handler.class).client();
                if (client)
                    MinecraftForge.EVENT_BUS.register(asmClass.newInstance());

            } catch (Exception e) {
                LogHelper.getLogger().fatal("Failed to register common EventHandlers");
            }
        }
    }

    @Override
    public void loadCommands() {
        ClientCommandHandler.instance.registerCommand(new CommandCreateSeed());
        ClientCommandHandler.instance.registerCommand(new CommandPrintSeed());
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
