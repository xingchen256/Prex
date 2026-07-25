package com.prex.prexmod.block;

import com.google.common.collect.Sets;
import moze_intel.projecte.utils.WorldHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;

public class TileSpeedUpdateBlock extends TileEntity {
    private static Set<String> internalBlacklist = Sets.newHashSet(new String[]{"moze_intel.projecte.gameObjs.tiles.DMPedestalTile", "Reika.ChromatiCraft.TileEntity.AOE.TileEntityAccelerator", "com.sci.torcherino.tile.TileTorcherino", "com.sci.torcherino.tile.TileCompressedTorcherino","com.prex.prexmod.block.TileSpeedUpdateBlock"});
    //黑名单,加速火把一类的
    private int tww;//加速等级
    private long lastTime= System.currentTimeMillis();//反加速使用系统时间
    private AxisAlignedBB boundingBox;
    @Override
    public void updateEntity(){
        if(worldObj.isRemote)
            return;
        if(System.currentTimeMillis() - lastTime >= 43){
            if(boundingBox == null){
                tww=((SpeedUpdateBlock)worldObj.getBlock(xCoord,yCoord,zCoord)).tww;
                boundingBox=getEffectBounds();
            }
            lastTime = System.currentTimeMillis();
            speedUpTileEntities(worldObj,tww*20,boundingBox);
        }//43毫秒1tick
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
    private void speedUpTileEntities(World world, int bonusTicks, AxisAlignedBB bBox) {
        //参数对应world,加速次数,范围
        if (bBox != null && bonusTicks != 0) {
            List<TileEntity> list = WorldHelper.getTileEntitiesWithinAABB(world, bBox);
            for (TileEntity tile : list) {
                if (tile.isInvalid() || internalBlacklist.contains(tile.getClass().getName())) continue;
                for (int i = 0; i < bonusTicks; ++i) {
                        tile.updateEntity();
                }
            }
        }
    }

    public static void blacklist(Class<? extends TileEntity> clazz) {
        internalBlacklist.add(clazz.getName());
    }
}
