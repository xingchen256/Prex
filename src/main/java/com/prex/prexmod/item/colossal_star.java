package com.prex.prexmod.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import moze_intel.projecte.api.item.IItemEmc;
import moze_intel.projecte.gameObjs.items.ItemPE;
import moze_intel.projecte.utils.AchievementHandler;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

import static com.prex.prexmod.ExampleMod.PREX_TAB;

public class colossal_star extends ItemPE implements IItemEmc {
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;
    private double[] MAX_MAGNUM_EMC = new double[]{838_860_800_000f,
            3_355_443_200_000f,13_421_772_800_000f,53_687_091_200_000f,
            214_748_364_800_000f,858_993_459_200_000f};

    public colossal_star()
    {
        this.setCreativeTab(PREX_TAB);
        this.setUnlocalizedName("colossa_star");
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        this.setNoRepair();
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return stack.hasTagCompound();
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        double starEmc = getEmc(stack);

        if (starEmc == 0)
        {
            return 1.0D;
        }

        return 1.0D - starEmc / MAX_MAGNUM_EMC[stack.getItemDamage()];
    }


    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        return stack;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
        if (!stack.hasTagCompound())
        {
            stack.stackTagCompound = new NBTTagCompound();
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if (stack.getItemDamage() > 5)
        {
            return "pe.debug.metainvalid";
        }

        return super.getUnlocalizedName()+ "_" + (stack.getItemDamage() + 1);
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs cTab, List list)
    {
        for (int i = 0; i < 6; ++i)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1)
    {
        return icons[MathHelper.clamp_int(par1, 0, 5)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        icons = new IIcon[6];

        for (int i = 0; i < 6; i++)
        {
            icons[i] = register.registerIcon("prex:star/colossal/"+ "s"+(i + 1));
        }
    }

    // -- IItemEmc -- //

    @Override
    public double addEmc(ItemStack stack, double toAdd)
    {
        double add = Math.min(getMaximumEmc(stack) - getStoredEmc(stack), toAdd);
        ItemPE.addEmcToStack(stack, add);
        return add;
    }

    @Override
    public double extractEmc(ItemStack stack, double toRemove)
    {
        double sub = Math.min(getStoredEmc(stack), toRemove);
        ItemPE.removeEmc(stack, sub);
        return sub;
    }

    @Override
    public double getStoredEmc(ItemStack stack)
    {
        return ItemPE.getEmc(stack);
    }

    @Override
    public double getMaximumEmc(ItemStack stack)
    {

        return MAX_MAGNUM_EMC[stack.getItemDamage()];
    }
}
