package tehnut.resourceful.crops.util.handler;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import tehnut.lib.util.helper.BlockHelper;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.block.BlockROre;

import java.util.Random;

public class GenerationHandler implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (ConfigHandler.enableWorldGeneration) {
            generateNether(world, random, chunkX * 16, chunkZ * 16);
            generateOverworld(world, random, chunkX * 16, chunkZ * 16);
        }
    }

    private void generateOverworld(World world, Random random, int chunkX, int chunkZ) {
        for (int k = 0; k < 10; k++) {
            int firstBlockXCoord = chunkX + random.nextInt(16);
            int firstBlockZCoord = chunkZ + random.nextInt(16);
            int gaianiteY = random.nextInt(16);

            BlockPos pos = new BlockPos(firstBlockXCoord, gaianiteY, firstBlockZCoord);

            new WorldGenMinable(BlockHelper.getBlock(BlockROre.class).getStateFromMeta(0), 4, BlockMatcher.forBlock(Blocks.STONE)).generate(world, random, pos);
        }
    }

    private void generateNether(World world, Random random, int chunkX, int chunkZ) {
        for (int k = 0; k < 10; k++) {
            int firstBlockXCoord = chunkX + random.nextInt(16);
            int firstBlockZCoord = chunkZ + random.nextInt(16);
            int gaianiteY = random.nextInt(256);

            BlockPos pos = new BlockPos(firstBlockXCoord, gaianiteY, firstBlockZCoord);

            new WorldGenMinable(BlockHelper.getBlock(BlockROre.class).getStateFromMeta(1), 4, BlockMatcher.forBlock(Blocks.NETHERRACK)).generate(world, random, pos);
        }
    }
}
