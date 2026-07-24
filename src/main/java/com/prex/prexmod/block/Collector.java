package com.prex.prexmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.blocks.BlockDirection;
import moze_intel.projecte.gameObjs.tiles.CollectorMK1Tile;
import moze_intel.projecte.gameObjs.tiles.CollectorMK2Tile;
import moze_intel.projecte.gameObjs.tiles.CollectorMK3Tile;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import static com.prex.prexmod.ExampleMod.PREX_TAB;

public class Collector extends BlockDirection
{
    @SideOnly(Side.CLIENT)
    private IIcon front;
    @SideOnly(Side.CLIENT)
    private IIcon top;
    private int tier;

    public Collector(int tier)
    {
        super(Material.glass);
        this.setBlockName("collector_mk" + tier);
        this.setLightLevel(6+tier);
        this.setHardness(0.3f);
        this.setBlockTextureName("prex:collector/"+PrExBlocks.Color[tier - 1]);
        this.setCreativeTab(PREX_TAB);
        this.tier = tier;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
            if(tier>3) player.openGui(PECore.instance, Constants.COLLECTOR1_GUI, world, x, y, z);
            else if(tier==1)player.openGui(PECore.instance, Constants.COLLECTOR1_GUI, world, x, y, z);
            else if (tier==2)player.openGui(PECore.instance, Constants.COLLECTOR2_GUI, world, x, y, z);
            else if (tier==3)player.openGui(PECore.instance, Constants.COLLECTOR3_GUI, world, x, y, z);
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
    public TileEntity createTileEntity(World world, int meta) {
        switch (tier) {
//            case 16:
//                return new TileCollector16();
//            case 15:
//                return new TileCollector15();
//            case 14:
//                return new TileCollector14();
//            case 13:
//                return new TileCollector13();
//            case 12:
//                return new TileCollector12();
//            case 11:
//                return new TileCollector11();
//            case 10:
//                return new TileCollector10();
//            case 9:
//                return new TileCollector9();
//            case 8:
//                return new TileCollector8();
//            case 7:
//                return new TileCollector7();
//            case 6:
//                return new TileCollector6();
//            case 5:
//                return new TileCollector5();
//            case 4:
//                return new TileCollector4();
            case 3:
                return new CollectorMK3Tile();
            case 2:
                return new CollectorMK2Tile();
            case 1:
                return new CollectorMK1Tile();
            default:
                return new TileCollector(tier-4);
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
        return ComparatorHelper.getForCollector(world, x, y, z);
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return true;
    }
}