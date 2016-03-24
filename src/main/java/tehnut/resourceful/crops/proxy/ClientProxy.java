package tehnut.resourceful.crops.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.tuple.Pair;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.api.ResourcefulAPI;
import tehnut.resourceful.crops.api.base.Seed;
import tehnut.resourceful.crops.api.registry.SeedRegistry;
import tehnut.resourceful.crops.item.ItemPouch;
import tehnut.resourceful.crops.item.ItemSeed;
import tehnut.resourceful.crops.item.ItemShard;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.repack.tehnut.lib.annot.Handler;
import tehnut.resourceful.repack.tehnut.lib.annot.Used;
import tehnut.resourceful.repack.tehnut.lib.iface.IMeshProvider;
import tehnut.resourceful.repack.tehnut.lib.iface.IVariantProvider;

@Used
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        for (ASMDataTable.ASMData data : ResourcefulCrops.instance.eventHandlers) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());
                boolean client = asmClass.getAnnotation(Handler.class).client();
                if (client)
                    MinecraftForge.EVENT_BUS.register(asmClass.newInstance());

            } catch (Exception e) {
                ResourcefulAPI.logger.fatal("Failed to register common EventHandlers");
            }
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        IItemColor seedColor = new IItemColor() {
            @Override
            public int getColorFromItemstack(ItemStack stack, int tintIndex) {
                Seed seed = SeedRegistry.getSeed(stack.getItemDamage());
                if (seed != null)
                    return seed.getColor().getRGB();

                return -1;
            }
        };

        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(
                seedColor,
                ItemRegistry.getItem(ItemPouch.class),
                ItemRegistry.getItem(ItemSeed.class),
                ItemRegistry.getItem(ItemShard.class)
        );
    }

    @Override
    public void tryHandleBlockModel(Block block, String name) {
        if (block instanceof IVariantProvider) {
            IVariantProvider variantProvider = (IVariantProvider) block;
            for (Pair<Integer, String> variant : variantProvider.getVariants())
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), variant.getLeft(), new ModelResourceLocation(new ResourceLocation(ModInformation.ID, name), variant.getRight()));
        }
    }

    @Override
    public void tryHandleItemModel(Item item, String name) {
        if (item instanceof IMeshProvider) {
            IMeshProvider meshProvider = (IMeshProvider) item;
            ModelLoader.setCustomMeshDefinition(item, meshProvider.getMeshDefinition());
            for (String variant : meshProvider.getVariants())
                ModelLoader.registerItemVariants(item, new ModelResourceLocation(new ResourceLocation(ModInformation.ID, "item/" + name), variant));
        } else if (item instanceof IVariantProvider) {
            IVariantProvider variantProvider = (IVariantProvider) item;
            for (Pair<Integer, String> variant : variantProvider.getVariants())
                ModelLoader.setCustomModelResourceLocation(item, variant.getLeft(), new ModelResourceLocation(new ResourceLocation(ModInformation.ID, "item/" + name), variant.getRight()));
        }
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
