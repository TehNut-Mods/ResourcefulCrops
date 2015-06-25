package tehnut.resourceful.crops.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tehnut.resourceful.crops.base.Seed;
import tehnut.resourceful.crops.base.SeedBuilder;
import tehnut.resourceful.crops.util.Utils;

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

        if (player.getHeldItem() != null) {
            ItemStack stack = player.getHeldItem();
            SeedBuilder builder = new SeedBuilder();

            builder.setName(stack.getDisplayName());
            builder.setTier(1);
            builder.setAmount(1);
            builder.setInput(Utils.ItemStackToString(new ItemStack(stack.getItem(), 1, stack.getItemDamage())));
            builder.setOutput(stack);
            builder.setColor(Color.ORANGE);

            commandList.add(builder.build());

            sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("chat.ResourcefulCrops.seeds.create", stack.getDisplayName())));
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
