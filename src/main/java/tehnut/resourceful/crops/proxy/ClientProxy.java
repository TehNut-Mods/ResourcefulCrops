package tehnut.resourceful.crops.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.command.CommandCreateSeed;
import tehnut.resourceful.crops.command.CommandPrintSeed;

public class ClientProxy extends CommonProxy {

    @Override
    public void load() {
        ConfigHandler.enableFancyRender = Minecraft.getMinecraft().gameSettings.fancyGraphics;
    }

    @Override
    public void loadCommands() {
        ClientCommandHandler.instance.registerCommand(new CommandCreateSeed());
        ClientCommandHandler.instance.registerCommand(new CommandPrintSeed());
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
