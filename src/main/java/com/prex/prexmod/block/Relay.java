package com.prex.prexmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.blocks.BlockDirection;
import moze_intel.projecte.gameObjs.tiles.RelayMK1Tile;
import moze_intel.projecte.gameObjs.tiles.RelayMK2Tile;
import moze_intel.projecte.gameObjs.tiles.RelayMK3Tile;
import moze_intel.projecte.gameObjs.tiles.TileEmc;
import moze_intel.projecte.utils.ComparatorHelper;
import moze_intel.projecte.utils.Constants;
import moze_intel.projecte.utils.GuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import static com.prex.prexmod.ExampleMod.PREX_TAB;

public class Relay extends BlockDirection
{
    @SideOnly(Side.CLIENT)
    private IIcon front;
    @SideOnly(Side.CLIENT)
    private IIcon top;
    private int tier;

    public Relay(int tier)
    {
        super(Material.rock);
        this.setBlockName("prex_relay_mk" + Integer.toString(tier));
        this.setLightLevel(3+tier);
        this.setCreativeTab(PREX_TAB);
        this.setHardness(10.0f);
        this.setBlockTextureName("prex:relay/"+PrExBlocks.Color[tier-1]);
        this.tier = tier;//tier>=1
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            switch (tier)
            {
                case 1:
                    player.openGui(PECore.instance, Constants.RELAY1_GUI, world, x, y, z);
                    break;
                case 2:
                    player.openGui(PECore.instance, Constants.RELAY2_GUI, world, x, y, z);
                    break;
                case 3:
                    player.openGui(PECore.instance, Constants.RELAY3_GUI, world, x, y, z);
                    break;
                default:
                    player.openGui(PECore.instance, Constants.RELAY1_GUI, world, x, y, z);
                    break;
            }
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entLiving, ItemStack stack)
    {
        setFacingMeta(world, x, y, z, ((EntityPlayer) entLiving));

        TileEntity tile = world.getTileEntity(x, y, z);

        if (stack.hasTagCompound() && stack.stackTagCompound.getBoolean("ProjectEBlock") && tile instanceof TileEmc)
        {
            stack.stackTagCompound.setInteger("x", x);
            stack.stackTagCompound.setInteger("y", y);
            stack.stackTagCompound.setInteger("z", z);

            tile.readFromNBT(stack.stackTagCompound);
        }
    }


    @Override
    public boolean hasTileEntity(int meta)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta)
    {
        switch (tier)
        {
            case 1: return new RelayMK1Tile();
            case 2: return new RelayMK2Tile();
            case 3: return new RelayMK3Tile();
            default: return new TitleRelay(tier-4);
        }
    }

    @Override
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int meta)
    {
        return ComparatorHelper.getForRelay(world, x, y, z);
    }
}