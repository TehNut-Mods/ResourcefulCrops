package tehnut.resourceful.crops.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import tehnut.resourceful.crops.api.ModInformation;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.ResourcefulCrops;
import tehnut.resourceful.crops.registry.BlockRegistry;
import tehnut.resourceful.crops.registry.ItemRegistry;

import java.util.List;
import java.util.Random;

public class BlockROre extends Block {

    public IIcon[] icons = new IIcon[2];
    Random random = new Random();

    public BlockROre() {
        super(Material.rock);

        setBlockName(ModInformation.ID + ".ore");
        setStepSound(soundTypeStone);
        setHardness(4.0F);
        setCreativeTab(ResourcefulCrops.tabResourcefulCrops);
        setHarvestLevel("pickaxe", 3);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.icons[0] = iconRegister.registerIcon(ModInformation.TEXLOC + "oreGaianite");
        this.icons[1] = iconRegister.registerIcon(ModInformation.TEXLOC + "oreGaianite_nether");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.icons[meta];
    
    }
    
       

    @Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta){
    	int iQuantity = 1;
    	int fortuneLevel = 0;
    	if (player instanceof FakePlayer && ConfigHandler.enableFakePlayerMining != true){
    		//This is a fake player, they get cobble
    		Block blockItem = Blocks.cobblestone;
			BlockROre.dropItem(world, x, y, z, new ItemStack(blockItem,1,0));
    		return;
    	}
    	else{	
    		//This is an actual player, generate items based on enchantment
    		boolean silk = EnchantmentHelper.getSilkTouchModifier(player);
    		if (silk) {
    			Block blockItem = BlockRegistry.ore;
    			BlockROre.dropItem(world, x, y, z, new ItemStack(blockItem,1,0));
    		}

    		else {
    			fortuneLevel = EnchantmentHelper.getFortuneModifier(player);

    			if (fortuneLevel > 0) {
    				int bonus = random.nextInt(fortuneLevel + 2) - 1;

    				if (bonus < 0)
    					bonus = 0;

    				iQuantity = 1  * (bonus + 1);
    			} else {
    				iQuantity = 1;
    			}

    			Item droppedItem = ItemRegistry.material;
    			System.out.println( "Not silktouch, dropped items" );
    			BlockROre.dropItem(world, x, y, z, new ItemStack(droppedItem,iQuantity,0));
    		}
    	}
    	return;
    }
    
	public static EntityItem dropItem(World world, double x, double y, double z, ItemStack iStack) {
		final Random rand = new Random();
		if (iStack == null)
			return null;
		EntityItem eItem = new EntityItem(world, x, y, z, iStack.copy());
		eItem.delayBeforeCanPickup = 10;
		eItem.motionX = (-0.1+0.2*rand.nextDouble()*.5);
		eItem.motionZ = (-0.1+0.2*rand.nextDouble()*.5);
		eItem.motionY = (0.2*rand.nextDouble()*.5);
		if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops") && !world.restoringBlockSnapshots) {
			world.spawnEntityInWorld(eItem);
		}
		return eItem;
	}
    
    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item id, CreativeTabs tab, List list) {
        for (int i = 0; i < 2; i++)
            list.add(new ItemStack(id, 1, i));
    
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    public Item getItemDropped(int damage, Random random, int fortune) {
    	if (ConfigHandler.enableFakePlayerMining != true)
    	return Blocks.cobblestone.getItemDropped(damage, random, fortune);
    	else
    	return ItemRegistry.material;	
    }

    public int quantityDroppedWithBonus(int fortune, Random random) {
        if (fortune > 0) {
            int bonus = random.nextInt(fortune + 2) - 1;

           if (bonus < 0)
               bonus = 0;

            return quantityDropped(random) * (bonus + 1);
        } else {
            return 1;
        }
    }

    @Override
    public int getExpDrop(IBlockAccess world, int meta, int fortune) {
        return MathHelper.getRandomIntegerInRange(random, 3, 7);
    }
}
