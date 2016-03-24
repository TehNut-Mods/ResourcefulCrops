package tehnut.resourceful.crops.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tehnut.lib.annot.Used;
import tehnut.lib.util.helper.ItemHelper;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.item.ItemPouch;
import tehnut.resourceful.crops.item.ItemSeed;
import tehnut.resourceful.crops.item.ItemShard;

@Used
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        IItemColor seedColor = new IItemColor() {
            @Override
            public int getColorFromItemstack(ItemStack stack, int tintIndex) {
                Seed seed = SeedRegistry.getSeed(stack.getItemDamage());
                if (seed != null)
                    return seed.getColor().getRGB();

                return -1;
            }
        };

        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(
                seedColor,
                ItemHelper.getItem(ItemPouch.class),
                ItemHelper.getItem(ItemSeed.class),
                ItemHelper.getItem(ItemShard.class)
        );
    }

    @Override
    public void addChatMessage(String string) {
        addChatMessage(string, 1);
    }

    @Override
    public void addChatMessage(String string, int id) {
        Minecraft minecraft = Minecraft.getMinecraft();
        GuiNewChat chat = minecraft.ingameGUI.getChatGUI();

        chat.printChatMessageWithOptionalDeletion(new TextComponentString(string), id);
    }
}
