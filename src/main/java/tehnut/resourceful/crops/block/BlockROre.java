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
import tehnut.resourceful.crops.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockROre extends Block {

    public IIcon[] icons = new IIcon[2];
    Random random = new Random();

    public BlockROre() {
        super(Material.rock);

        setBlockName(ModInformation.ID + ".ore");
        setCreativeTab(ResourcefulCrops.tabResourcefulCrops);
        setStepSound(soundTypeStone);
        setHardness(4.0F);
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
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {

        if (Utils.isFakePlayer(player) && !ConfigHandler.enableFakePlayerMining) {
            Block blockItem = Blocks.cobblestone;
            dropItem(world, x, y, z, new ItemStack(blockItem, 1, 0));
        } else {
            boolean silk = EnchantmentHelper.getSilkTouchModifier(player);
            if (silk) {
                dropItem(world, x, y, z, new ItemStack(this, 1, meta));
            } else {
                int fortune = EnchantmentHelper.getFortuneModifier(player);
                int dropAmount;

                if (fortune > 0) {
                    int bonus = random.nextInt(fortune + 2) - 1;

                   if (bonus < 0)
                       bonus = 0;

                    dropAmount = bonus + 1;
                } else {
                    dropAmount = 1;
                }

                dropItem(world, x, y, z, new ItemStack(ItemRegistry.material, dropAmount));
            }
        }
    }

    public static EntityItem dropItem(World world, double x, double y, double z, ItemStack stack) {
        if (stack == null)
            return null;

        EntityItem entityItem = new EntityItem(world, x, y, z, stack.copy());
        entityItem.delayBeforeCanPickup = 10;

        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops") && !world.restoringBlockSnapshots)
            world.spawnEntityInWorld(entityItem);

        return entityItem;
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 2; i++)
            list.add(new ItemStack(item, 1, i));
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    public Item getItemDropped(int damage, Random random, int fortune) {
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
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        int count = quantityDropped(metadata, fortune, world.rand);
        for (int i = 0; i < count; i++) {
            Item item = getItemDropped(metadata, world.rand, fortune);

            if (item != null)
                if (ConfigHandler.enableFakePlayerMining)
                    ret.add(new ItemStack(item, 1, damageDropped(metadata)));
        }

        return ret;
    }

    @Override
    public int getExpDrop(IBlockAccess world, int meta, int fortune) {
        return MathHelper.getRandomIntegerInRange(random, 3, 7);
    }
}
