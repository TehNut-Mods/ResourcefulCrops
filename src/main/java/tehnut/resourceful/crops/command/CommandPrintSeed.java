package tehnut.resourceful.crops.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import tehnut.resourceful.crops.registry.SeedRegistry;
import tehnut.resourceful.crops.util.serialization.SeedCreator;

public class CommandPrintSeed extends CommandBase {

    @Override
    public String getCommandName() {
        return "printSeeds";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (!CommandCreateSeed.getCommandList().isEmpty()) {

            String fileName = "PrintedSeeds";

            if (args.length > 0)
                fileName = args[0];

            SeedCreator.createJsonFromSeeds(SeedRegistry.seedBuilder, CommandCreateSeed.commandList, fileName);

            sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("chat.ResourcefulCrops.seeds.print", fileName)));
        } else {
            sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chat.ResourcefulCrops.seeds.print.empty")));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
