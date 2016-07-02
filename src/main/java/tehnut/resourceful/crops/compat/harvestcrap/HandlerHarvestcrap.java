package tehnut.resourceful.crops.compat.harvestcrap;

import com.pam.harvestcraft.HarvestCraft;
import com.pam.harvestcraft.addons.RightClickHarvesting;
import com.pam.harvestcraft.blocks.growables.BlockPamFruit;
import com.pam.harvestcraft.blocks.growables.BlockPamFruitLog;
import com.pam.harvestcraft.blocks.growables.PamCropGrowable;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockPotato;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tehnut.resourceful.crops.api.ResourcefulAPI;

import java.util.Iterator;
import java.util.List;

public class HandlerHarvestcrap {

	public static void init() {
		ResourcefulAPI.logger.info("Overriding Pam's Harvestcraft right click harvesting behavior.");
		MinecraftForge.EVENT_BUS.unregister(RightClickHarvesting.instance);
		MinecraftForge.EVENT_BUS.register(new HandlerHarvestcrap());
	}

	// All code below here is ripped from Pam's Harvestcraft (RightClickHarvesting.class) used soley with the intent to
	// patch poor behavior.

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
		if(HarvestCraft.config.enableEasyHarvest) {
			if(!event.getWorld().isRemote) {
				if(event.getEntityPlayer() != null) {
					if(event.getHand() == EnumHand.MAIN_HAND) {
						IBlockState blockState = event.getWorld().getBlockState(event.getPos());
						if(blockState.getBlock() instanceof BlockCrops) {
							harvestCrops(blockState, event.getEntityPlayer(), event.getWorld(), event.getPos());
						}

						if(blockState.getBlock() instanceof BlockPamFruit || blockState.getBlock() instanceof BlockPamFruitLog) {
							harvestFruit(blockState, event.getEntityPlayer(), event.getWorld(), event.getPos());
						}
					}
				}
			}
		}
	}

	private static void harvestCrops(IBlockState blockState, EntityPlayer player, World world, BlockPos blockPos) {
		BlockCrops crops = (BlockCrops)blockState.getBlock();
		// Start patch
		if (CompatibilityHarvestcrap.rightClickBlacklist.contains(crops.getClass()))
			return;
		// End patch
		if(crops.isMaxAge(blockState)) {
			ItemStack stack = player.getHeldItemMainhand();
			int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
			List drops = crops.getDrops(world, blockPos, blockState, fortune);
			Item seedItem = crops.getItemDropped(blockState, world.rand, fortune);
			Iterator iterator;
			ItemStack drop;
			if(seedItem != null) {
				label42: {
					iterator = drops.iterator();

					do {
						if(!iterator.hasNext()) {
							break label42;
						}

						drop = (ItemStack)iterator.next();
					} while(drop.getItem() == seedItem && !(crops instanceof BlockCarrot) && !(crops instanceof BlockPotato));

					iterator.remove();
				}
			}

			world.setBlockState(blockPos, crops.getStateFromMeta(0));
			iterator = drops.iterator();

			while(iterator.hasNext()) {
				drop = (ItemStack)iterator.next();
				dropItem(drop, world, blockPos);
			}
		}

	}

	private static void harvestFruit(IBlockState blockState, EntityPlayer player, World world, BlockPos blockPos) {
		PamCropGrowable blockPamFruit = (PamCropGrowable)blockState.getBlock();
		if(blockPamFruit.isMature(blockState)) {
			ItemStack stack = player.getHeldItemMainhand();
			int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
			List drops = blockPamFruit.getDrops(world, blockPos, blockState, fortune);
			if(drops.size() > 0) {
				drops.remove(drops.size() - 1);
			}

			world.setBlockState(blockPos, blockState.withProperty(blockPamFruit.getAgeProperty(), 0), 3);
			Iterator var8 = drops.iterator();

			while(var8.hasNext()) {
				ItemStack drop = (ItemStack)var8.next();
				dropItem(drop, world, blockPos);
			}
		}

	}

	private static void dropItem(ItemStack itemStack, World world, BlockPos pos) {
		if(!world.restoringBlockSnapshots && !world.isRemote) {
			float f = 0.5F;
			double d0 = (double)(world.rand.nextFloat() * f) + 0.25D;
			double d1 = (double)(world.rand.nextFloat() * f) + 0.25D;
			double d2 = (double)(world.rand.nextFloat() * f) + 0.25D;
			EntityItem entityItem = new EntityItem(world, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, itemStack);
			entityItem.setDefaultPickupDelay();
			world.spawnEntityInWorld(entityItem);
		}
	}
}
