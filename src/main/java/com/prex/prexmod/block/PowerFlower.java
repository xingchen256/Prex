package com.prex.prexmod.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import static com.prex.prexmod.ExampleMod.PREX_TAB;

public class PowerFlower extends BlockContainer {
    public static long[] gen = {
            1L,                    // 0（没有定义）
            384L,                  // 1
            1152L,                 // 2
            3840L,                 // 3
            61440L,                // 4
            245760L,               // 5
            983040L,               // 6
            39321600L,              // 7
            157286400L,             // 8
            629145600L,             // 9
            2516582400L,            // 10
            10066329600L,           // 11
            4026531840000L,           // 12
            16106127360000L,          // 13
            64424509440000L,          // 14
            257698037760000L,         // 15
            200000000000000000L     // 16
    };
    public int matter;

    public PowerFlower(int matter) {
        super(Material.glass);

        this.matter = matter;
        setCreativeTab(PREX_TAB);
        setBlockName("power_flower_mk"+this.matter);
        setHardness(1F);
        setResistance(10F);
        setLightLevel(6+this.matter);
        setBlockTextureName("prex:collector/"+PrExBlocks.Color[matter-1]);
    }
    @Override
    public int getRenderType(){
        return -1;
    }
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
//    @Override
//    public int getLightValue(IBlockAccess world, int x, int y, int z) {
//        return 11;
//    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TilePowerFlower();
    }


    @Override
    public boolean onBlockActivated(
            World world,
            int x, int y, int z,
            EntityPlayer player,
            int side, float hitX, float hitY, float hitZ
    ){

        if(!world.isRemote){

            TileEntity te = world.getTileEntity(x,y,z);

            if(te instanceof TilePowerFlower){

                player.addChatMessage(
                        new ChatComponentText(
                                ((TilePowerFlower) te).getOwerName()
                        )
                );
            }
        }

        return true;
    }

    @Override
    public void onBlockPlacedBy(
            World world,
            int x, int y, int z,
            EntityLivingBase entity,
            ItemStack stack
    ){
        if(world.isRemote)return;
        TileEntity te = world.getTileEntity(x,y,z);

        if(te instanceof TilePowerFlower){
            TilePowerFlower tile=(TilePowerFlower)te;
            tile.setOwnerUUID(entity.getUniqueID());
            tile.setOwerName(entity.getCommandSenderName());
        }
    }

}