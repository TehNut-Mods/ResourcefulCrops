package tehnut.resourceful.crops.network;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.util.ChatUtil;

public class ResourcefulPacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(ModInformation.NAME);

    public static void init() {
        INSTANCE.registerMessage(ChatUtil.PacketNoSpamChat.Handler.class, ChatUtil.PacketNoSpamChat.class, 0, Side.CLIENT);
    }
}
