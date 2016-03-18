package tehnut.resourceful.crops.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.repack.tehnut.lib.annot.Handler;
import tehnut.resourceful.repack.tehnut.lib.iface.IProxy;

public class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        for (ASMDataTable.ASMData data : ResourcefulCrops.instance.eventHandlers) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());
                boolean client = asmClass.getAnnotation(Handler.class).client();
                if (!client)
                    MinecraftForge.EVENT_BUS.register(asmClass.newInstance());

            } catch (Exception e) {
                ResourcefulAPI.logger.fatal("Failed to register common EventHandlers");
            }
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    public void tryHandleBlockModel(Block block, String name) {
        // NO-OP
    }

    public void tryHandleItemModel(Item item, String name) {
        // NO-OP
    }

    public void addChatMessage(String string) {
        // NO-OP
    }

    /**
     * Sends a message to the client that gets replaced when the same
     * messages are sent again. This reduces the amount of chat spam
     * players end up with when checking things repeatedly.
     *
     * @param string - String message to send to player
     * @param id     - An ID for the message. Gets replaced by any other message that uses the same ID.
     */
    public void addChatMessage(String string, int id) {

    }
}
