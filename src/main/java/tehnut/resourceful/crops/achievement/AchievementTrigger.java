package tehnut.resourceful.crops.achievement;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import tehnut.lib.annot.Handler;
import tehnut.lib.annot.Used;
import tehnut.lib.util.helper.ItemHelper;
import tehnut.resourceful.crops.item.*;
import tehnut.resourceful.crops.registry.AchievementRegistry;
import tehnut.resourceful.crops.util.Utils;

@Handler
public class AchievementTrigger {

    @SubscribeEvent
    @Used
    public void onItemPickedUp(PlayerEvent.ItemPickupEvent event) {
        ItemStack pickup = event.pickedUp.getEntityItem();

        if (pickup.getItem() == ItemHelper.getItem(ItemMaterial.class))
            event.player.addStat(AchievementRegistry.getEssence, 1);

        if (pickup.getItem() == ItemHelper.getItem(ItemShard.class))
            event.player.addStat(AchievementRegistry.getShard, 1);
    }

    @SubscribeEvent
    @Used
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack output = event.crafting;

        if (output.getItem() == ItemHelper.getItem(ItemStone.class)) {
            int tier = Utils.getItemDamage(output);

            event.player.addStat(AchievementRegistry.getStone[tier], 1);
        }

        if (output.getItem() == ItemHelper.getItem(ItemSeed.class))
            event.player.addStat(AchievementRegistry.getSeed, 1);

        if (output.getItem() == ItemHelper.getItem(ItemPouch.class))
            event.player.addStat(AchievementRegistry.getPouch, 1);
    }

    public static void triggerAchievement(EntityPlayer player, Achievement achievement) {
        player.addStat(achievement, 1);
    }
}
