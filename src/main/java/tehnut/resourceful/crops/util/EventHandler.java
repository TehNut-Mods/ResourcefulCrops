package tehnut.resourceful.crops.util;

import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tehnut.resourceful.crops.core.RegistrarResourcefulCrops;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void onBonemeal(BonemealEvent event) {
        if (event.getBlock().getBlock() == RegistrarResourcefulCrops.CROP)
            event.setCanceled(true);
    }
}
