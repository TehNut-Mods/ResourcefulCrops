package tehnut.resourceful.crops.achievement;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import tehnut.resourceful.crops.registry.AchievementRegistry;

public class AchievementRCrops extends Achievement {

    public AchievementRCrops(String name, int x, int y, ItemStack displayStack, Achievement parent) {
        super("ResourcefulCrops." + name, "ResourcefulCrops." + name, x, y, displayStack, parent);
        AchievementRegistry.achievements.add(this);

        registerStat();
    }
}
