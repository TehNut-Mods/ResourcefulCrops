package tehnut.resourceful.crops.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.resourceful.crops.block.tile.TileSeedContainer;
import tehnut.resourceful.crops.core.RegistrarResourcefulCrops;
import tehnut.resourceful.crops.core.data.Seed;
import tehnut.resourceful.crops.item.ItemResourceful;
import tehnut.resourceful.crops.util.Util;

@SideOnly(Side.CLIENT)
@SuppressWarnings("ConstantConditions")
public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        super.init();

        Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            Seed seed = ((ItemResourceful) stack.getItem()).getSeed(stack);
            if (seed == null || (stack.getItem() == RegistrarResourcefulCrops.POUCH && tintIndex != 1))
                return -1;

            return seed.getColor().getRGB();
        }, RegistrarResourcefulCrops.SEED, RegistrarResourcefulCrops.POUCH, RegistrarResourcefulCrops.SHARD);

        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, blockAccess, pos, tintIndex) -> {
            if (blockAccess == null || pos == null)
                return -1;
            TileSeedContainer seedContainer = Util.getSeedContainer(blockAccess, pos);
            if (seedContainer == null)
                return -1;

            Seed seed = RegistrarResourcefulCrops.SEEDS.getValue(seedContainer.getSeedKey());
            if (seed == null)
                return -1;

            return seed.getColor().getRGB();
        }, RegistrarResourcefulCrops.CROP);
    }

    @Override
    public void postInit() {
        super.postInit();
    }
}
