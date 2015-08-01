package tehnut.resourceful.crops.achievement;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import tehnut.resourceful.crops.registry.AchievementRegistry;
import tehnut.resourceful.crops.registry.ItemRegistry;
import tehnut.resourceful.crops.util.Utils;

public class AchievementTrigger {

    @SubscribeEvent
    public void onItemPickedUp(PlayerEvent.ItemPickupEvent event) {
        ItemStack pickup = event.pickedUp.getEntityItem();

        if (pickup.getItem() == ItemRegistry.material)
            event.player.addStat(AchievementRegistry.getEssence, 1);

        if (pickup.getItem() == ItemRegistry.shard)
            event.player.addStat(AchievementRegistry.getShard, 1);
    }

    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack output = event.crafting;

        if (output.getItem() == ItemRegistry.stone) {
            int tier = Utils.getItemDamage(output);

            event.player.addStat(AchievementRegistry.getStone[tier], 1);
        }

        if (output.getItem() == ItemRegistry.seed)
            event.player.addStat(AchievementRegistry.getSeed, 1);

        if (output.getItem() == ItemRegistry.pouch)
            event.player.addStat(AchievementRegistry.getPouch, 1);
    }

    public static void triggerAchievement(EntityPlayer player, Achievement achievement) {
        player.addStat(achievement, 1);
    }
}
