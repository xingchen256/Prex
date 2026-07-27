package com.prex.prexmod.block;

import moze_intel.projecte.utils.WorldHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class TileSpeedUpdatePowerFlower extends TileEntity {
    private int tww;//加速等级
    private long lastTime= System.currentTimeMillis();//反加速使用系统时间
    private AxisAlignedBB boundingBox;
    private int qww;
    @Override
    public void updateEntity(){
        if(worldObj.isRemote)
            return;
        if(System.currentTimeMillis() - lastTime >= 990){//这样会导致一个bug,21~23tick才会执行一次,但可以优化tps
            if(boundingBox == null){
                tww=((SpeedUpdatePowerFlower)worldObj.getBlock(xCoord,yCoord,zCoord)).tww;
                boundingBox=getEffectBounds();
                qww=tww*60;
            }
            lastTime = System.currentTimeMillis();
            updatePowerFlower(worldObj,qww,boundingBox);
        }
    }
    public void updatePowerFlower(World world, int bonusTicks, AxisAlignedBB bBox){
        if (bBox != null) {
            List<TileEntity> list = WorldHelper.getTileEntitiesWithinAABB(world, bBox);
            for (TileEntity tile : list) {
                if (tile instanceof TilePowerFlower) ((TilePowerFlower) tile).addTick(bonusTicks*20);
            }
        }
    }
    public AxisAlignedBB getEffectBounds() {
        int qqw=Math.min(tww*4,8) ;
        return AxisAlignedBB.getBoundingBox(
                xCoord - qqw,
                yCoord - Math.min(qqw,4) ,
                zCoord - qqw,
                xCoord + qqw,
                yCoord + Math.min(qqw,4),
                zCoord + qqw
        );
    }
}
