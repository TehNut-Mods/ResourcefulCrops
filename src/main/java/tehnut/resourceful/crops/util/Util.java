package tehnut.resourceful.crops.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import tehnut.resourceful.crops.block.tile.TileSeedContainer;

import javax.annotation.Nullable;

public class Util {

    @Nullable
    public static TileSeedContainer getSeedContainer(IBlockAccess world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileSeedContainer)
            return (TileSeedContainer) tile;

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
}
