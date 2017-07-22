package tehnut.resourceful.crops.util;

import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tehnut.resourceful.crops.block.tile.TileSeedContainer;
import tehnut.resourceful.crops.core.RegistrarResourcefulCrops;
import tehnut.resourceful.crops.core.data.Seed;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void onBonemeal(BonemealEvent event) {
        TileSeedContainer seedContainer = Util.getTile(TileSeedContainer.class, event.getWorld(), event.getPos());
        if (seedContainer != null) {
            Seed seed = RegistrarResourcefulCrops.SEEDS.getValue(seedContainer.getSeedKey());
            if (!seed.canFertilize())
                event.setCanceled(true);
        }
    }
}
