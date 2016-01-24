package tehnut.resourceful.crops.api;

public class ModInformation {

    public static final String NAME = "Resourceful Crops";
    public static final String ID = "ResourcefulCrops";
    public static final String APIID = ID + "|API";
    public static final String RESLOC = ID.toLowerCase() + ":";
    public static final String VERSION = "@VERSION@";
    public static final String REQUIRED = "after:neotech@[2.2.5,)";
    public static final String GUIFACTORY = "tehnut.resourceful.crops.client.gui.ConfigGuiFactory";
    public static final String CLIENTPROXY = "tehnut.resourceful.crops.proxy.ClientProxy";
    public static final String COMMONPROXY = "tehnut.resourceful.crops.proxy.CommonProxy";
}
