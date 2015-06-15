package tehnut.resourceful.crops.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.base.Seed;
import tehnut.resourceful.crops.blocks.BlockRCrop;
import tehnut.resourceful.crops.registry.SeedRegistry;
import tehnut.resourceful.crops.tile.TileRCrop;
import tehnut.resourceful.crops.util.Utils;

public class RenderRCrop implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        renderer.setOverrideBlockTexture(block.getIcon(0, 0));
        renderer.renderCrossedSquares(Blocks.stone, 0, 0, 0);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        IIcon blockIcon = block.getIcon(world, x, y, z, 0);
        renderer.setOverrideBlockTexture(blockIcon);
        float renderY = y - 0.06F;
        renderer.drawCrossedSquares(blockIcon, x, renderY, z, 1.0F);
        renderer.clearOverrideBlockTexture();
        int meta = world.getBlockMetadata(x, y, z);
        TileEntity cropTile = world.getTileEntity(x, y, z);
        if (cropTile instanceof TileRCrop) {
            String seedName = ((TileRCrop) cropTile).getSeedName();

            if (Utils.isValidSeed(seedName)) {
                Seed seed = SeedRegistry.getSeed(seedName);
                IIcon icon = ((BlockRCrop) block).cropOverlay[meta];

                Tessellator tessellator = Tessellator.instance;
                tessellator.setColorRGBA(seed.getColor().getRed(), seed.getColor().getGreen(), seed.getColor().getBlue(), seed.getColor().getAlpha());

                if (meta == 0)
                    renderer.drawCrossedSquares(icon, x, renderY, z, 1.0F);
                else if (meta == 1)
                    renderer.drawCrossedSquares(icon, x, renderY, z, 1.0F);
                else if (meta == 2)
                    renderer.drawCrossedSquares(icon, x, renderY, z, 1.0F);
                else if (meta == 3)
                    renderer.drawCrossedSquares(icon, x, renderY, z, 1.0F);
                else if (meta == 4)
                    renderer.drawCrossedSquares(icon, x, renderY, z, 1.0F);
                else if (meta == 5)
                    renderer.drawCrossedSquares(icon, x, renderY, z, 1.0F);
                else if (meta == 6)
                    renderer.drawCrossedSquares(icon, x, renderY + 0.0625F, z, 1.0F);
                else
                    renderer.drawCrossedSquares(icon, x, renderY + 0.125F, z, 1.0F);
            }
        }

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return ResourcefulCrops.renderIDCrop;
    }
}
