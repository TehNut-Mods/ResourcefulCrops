package tehnut.resourceful.crops2.util;

import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tehnut.resourceful.crops2.core.ModObjects;

public class EventHandler {

    @SubscribeEvent
    public void onBonemeal(BonemealEvent event) {
        if (event.getBlock().getBlock() == ModObjects.CROP)
            event.setCanceled(true);
    }
}
