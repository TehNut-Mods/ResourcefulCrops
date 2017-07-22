package tehnut.resourceful.crops.util;

import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.apache.commons.lang3.math.NumberUtils;
import tehnut.resourceful.crops.ResourcefulCrops;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class Util {

    @Nullable
    public static <T extends TileEntity> T getTile(Class<T> type, IBlockAccess world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile.getClass().isAssignableFrom(type))
            return (T) tile;

        return null;
    }

    public static String cleanString(String dirtyString) {
        return dirtyString.replaceAll("[^a-zA-Z0-9]","").trim();
    }

    public static String prettifyString(String ugly) {
        StringBuilder stringBuilder = new StringBuilder();

        boolean toUpper = true;
        for (int i = 0; i < ugly.toCharArray().length; i++) {
            char c = ugly.toCharArray()[i];

            if (toUpper) {
                stringBuilder.append(Character.toUpperCase(c));
                toUpper = false;
            } else if (Character.isDigit(c) && (i > 0 && !Character.isDigit(ugly.toCharArray()[i - 1]))) {
                stringBuilder.append(' ').append(c);
            } else if ((c == '-' || c == '_') || Character.isWhitespace(c)) {
                stringBuilder.append(' ');
                toUpper = true;
            } else {
                stringBuilder.append(Character.toLowerCase(c));
            }
        }

        return stringBuilder.toString();
    }

    // Thanks Paul
    public static int getStackColor(ItemStack stack) {
        IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, null, null);
        TextureAtlasSprite sprite = model.getParticleTexture();
        if (sprite == null) { // Item doesn't have a TAS and is rendered some other way.
            ResourcefulCrops.debug("[ERROR] Error generating color from stack {} (Null TAS)", stack);
            return 0xFF000000;
        }
        int[] pixels = sprite.getFrameTextureData(0)[0];
        int r = 0, g = 0, b = 0, count = 0;
        for (int argb : pixels) {
            int ca = argb >> 24 & 0xFF;
            int cr = argb >> 16 & 0xFF;
            int cg = argb >> 8 & 0xFF;
            int cb = argb & 0xFF;
            if (ca > 0x7F && NumberUtils.max(cr, cg, cb) > 0x1F) {
                r += cr;
                g += cg;
                b += cb;
                count++;
            }
        }
        if (count > 0) {
            r /= count;
            g /= count;
            b /= count;
        }
        return 0xFF000000 | r << 16 | g << 8 | b;
    }

    @Nonnull
    public static String getPropertyString(Map<IProperty<?>, Comparable<?>> values) {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<IProperty<?>, Comparable<?>> entry : values.entrySet()) {
            if (builder.length() != 0)
                builder.append(",");

            IProperty<?> prop = entry.getKey();
            builder.append(prop.getName());
            builder.append("=");
            builder.append(getPropertyName(prop, entry.getValue()));
        }

        if (builder.length() == 0)
            builder.append("normal");

        return builder.toString();
    }

    @Nonnull
    private static <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> value) {
        return property.getName((T)value);
    }
}
