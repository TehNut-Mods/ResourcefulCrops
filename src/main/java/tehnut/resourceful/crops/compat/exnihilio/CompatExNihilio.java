package tehnut.resourceful.crops.compat.exnihilio;

import exnihilo.registries.SieveRegistry;
import net.minecraft.block.Block;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.api.base.Compat;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.compat.ICompatibility;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.crops.util.Utils;

public class CompatExNihilio implements ICompatibility {

    @Override
    public void loadCompatibility() {
        for (Seed seed : SeedRegistry.getSeedList()) {
            if (seed.getCompat() != null && seed.getCompat().getCompatExNihilio() != null) {
                Compat.CompatExNihilio compatExNihilio = seed.getCompat().getCompatExNihilio();
                if (compatExNihilio.getSourceBlock() != null)
                    SieveRegistry.register(compatExNihilio.getSourceBlock().getBlock(), compatExNihilio.getSourceBlock().getMeta(), ItemRegistry.seed, SeedRegistry.getIndexOf(seed), compatExNihilio.getSieveChance());
            }
        }

        String[] blockInfo = ConfigHandler.gaianiteSieveBlock.split(":");

        if (ConfigHandler.gaianiteSieveChance > 0)
            SieveRegistry.register(Block.getBlockFromName(blockInfo[0] + ":" + blockInfo[1]), Integer.parseInt(blockInfo[2]), ItemRegistry.material, 0, ConfigHandler.gaianiteSieveChance);
    }

    @Override
    public String getModId() {
        return "exnihilo";
    }

    @Override
    public boolean enableCompat() {
        return true;
    }
}
