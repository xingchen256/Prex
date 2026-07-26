package com.prex.prexmod.block;

import com.prex.prexmod.emc.PrEXEMC;
import moze_intel.projecte.utils.WorldHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.math.BigInteger;
import java.util.List;

public class TileSpeedUpdatePowerFlower extends TileEntity {
    private int tww;//加速等级
    private long lastTime= System.currentTimeMillis();//反加速使用系统时间
    private AxisAlignedBB boundingBox;
    @Override
    public void updateEntity(){
        if(worldObj.isRemote)
            return;
        if(System.currentTimeMillis() - lastTime >= 480){
            if(boundingBox == null){
                tww=((SpeedUpdatePowerFlower)worldObj.getBlock(xCoord,yCoord,zCoord)).tww;
                boundingBox=getEffectBounds();
            }
            lastTime = System.currentTimeMillis();
            updatePowerFlower(worldObj,BigInteger.valueOf(tww* 20L),boundingBox);
        }
    }
    public void updatePowerFlower(World world, BigInteger bonusTicks, AxisAlignedBB bBox){
        if (bBox != null) {
            List<TileEntity> list = WorldHelper.getTileEntitiesWithinAABB(world, bBox);
            for (TileEntity tile : list) {
                if (tile instanceof TilePowerFlower){
                    TilePowerFlower flower = (TilePowerFlower) tile;
                    EntityPlayer p=world.func_152378_a(flower.owner);
                    if(p==null)continue;
                    PrEXEMC.add(p,
                            BigInteger.valueOf(PowerFlower.gen[((TilePowerFlower) tile).matter]).multiply(bonusTicks)
                    );//给力量花盆的主人添加力量花盆等级的emc*加速倍率
                }
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
