package tehnut.resourceful.crops.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.annot.Handler;
import tehnut.resourceful.crops.util.helper.LogHelper;

public class CommonProxy {

    public void preInit() {
        for (ASMDataTable.ASMData data : ResourcefulCrops.instance.eventHandlers) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());
                boolean client = asmClass.getAnnotation(Handler.class).client();
                if (!client)
                    MinecraftForge.EVENT_BUS.register(asmClass.newInstance());

            } catch (Exception e) {
                LogHelper.getLogger().fatal("Failed to register common EventHandlers");
            }
        }
    }

    public void loadCommands() {

    }

    public void loadRenders() {

    }

    public void addChatMessage(String string) {

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
