package tehnut.resourceful.crops.proxy;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tehnut.lib.annot.Used;
import tehnut.lib.util.helper.BlockHelper;
import tehnut.lib.util.helper.ItemHelper;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.block.BlockRCrop;
import tehnut.resourceful.crops.item.ItemPouch;
import tehnut.resourceful.crops.item.ItemSeed;
import tehnut.resourceful.crops.item.ItemShard;
import tehnut.resourceful.crops.tile.TileRCrop;

@Used
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        IBlockColor cropColor = new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, IBlockAccess blockAccess, BlockPos pos, int tintIndex) {
                TileEntity cropTile = blockAccess.getTileEntity(pos);
                if (cropTile != null && cropTile instanceof TileRCrop) {
                    Seed seed = ResourcefulAPI.SEEDS.getObject(((TileRCrop) cropTile).getSeedName());
                    if (seed != null)
                        return seed.getColor().getRGB();
                }

                return -1;
            }
        };

        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(
                cropColor,
                BlockHelper.getBlock(BlockRCrop.class)
        );

        IItemColor seedColor = new IItemColor() {
            @Override
            public int getColorFromItemstack(ItemStack stack, int tintIndex) {
                Seed seed = ResourcefulAPI.SEEDS.getRaw(stack.getItemDamage());
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
