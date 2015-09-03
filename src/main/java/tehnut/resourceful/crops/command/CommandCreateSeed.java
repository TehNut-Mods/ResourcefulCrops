package tehnut.resourceful.crops.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.base.SeedBuilder;
import tehnut.resourceful.crops.util.Utils;
import tehnut.resourceful.crops.util.helper.ItemHelper;

import java.awt.*;
import java.util.ArrayList;

public class CommandCreateSeed extends CommandBase {

    public static ArrayList<Seed> commandList = new ArrayList<Seed>();

    @Override
    public String getCommandName() {
        return "createSeed";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        World world = sender.getEntityWorld();
        EntityPlayer player = world.getPlayerEntityByName(sender.getCommandSenderName());

        if (args.length > 0 && args[0].equals("help")) {
            for (int i = 0; i < 4; i++)
                sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chat.ResourcefulCrops.seeds.create.help." + i)));
            return;
        }

        if (player.getHeldItem() != null) {
            ItemStack stack = player.getHeldItem();
            SeedBuilder builder = new SeedBuilder();

            String name = stack.getDisplayName();
            int tier = 1;
            int amount = 1;
            Color color = Color.CYAN;

            for (String arg : args) {
                if (arg.startsWith("name") && arg.contains(":"))
                    name = arg.split(":")[1].replace("_", " ");

                if (arg.startsWith("tier") && arg.contains(":"))
                    tier = parseIntBounded(sender, arg.split(":")[1], 1, 4);

                if (arg.startsWith("amount") && arg.contains(":"))
                    amount = parseIntBounded(sender, arg.split(":")[1], 1, 64);

                if (arg.startsWith("color") && arg.contains(":")) {
                    try {
                        String colorString = arg.split(":")[1];
                        if (!colorString.startsWith("#"))
                            colorString = "#" + colorString;
                        color = Color.decode(colorString);
                    } catch (NumberFormatException e) {
                        sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("chat.ResourcefulCrops.seeds.create.nfe", "Color")));
                    }
                }
            }

            builder.setName(name);
            builder.setTier(tier);
            builder.setAmount(amount);
            builder.setInput(ItemHelper.getItemString(new ItemStack(stack.getItem(), 1, stack.getItemDamage())));
            builder.setOutput(ItemHelper.getItemString(stack));
            builder.setColor(color);

            commandList.add(builder.build());

            sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("chat.ResourcefulCrops.seeds.create", name)));
        } else {
            sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chat.ResourcefulCrops.seeds.create.empty")));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    public static ArrayList<Seed> getCommandList() {
        return new ArrayList<Seed>(commandList);
    }
}
