package tehnut.resourceful.crops.util.handler;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.registry.BlockRegistry;

import java.util.Random;

public class GenerationHandler implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
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

            new WorldGenMinable(BlockRegistry.ore, 0, 4, Blocks.stone).generate(world, random, firstBlockXCoord, gaianiteY, firstBlockZCoord);
        }
    }

    private void generateNether(World world, Random random, int chunkX, int chunkZ) {
        for (int k = 0; k < 10; k++) {
            int firstBlockXCoord = chunkX + random.nextInt(16);
            int firstBlockZCoord = chunkZ + random.nextInt(16);
            int gaianiteY = random.nextInt(256);

            new WorldGenMinable(BlockRegistry.ore, 1, 4, Blocks.netherrack).generate(world, random, firstBlockXCoord, gaianiteY, firstBlockZCoord);
        }
    }
}
