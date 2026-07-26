package com.prex.prexmod.block;

import com.google.common.collect.Sets;
import moze_intel.projecte.utils.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;

import java.util.List;
import java.util.Set;

public class TileSpeedUpdateBlock extends TileEntity {
    private static Set<String> internalBlacklist = Sets.newHashSet(new String[]{"moze_intel.projecte.gameObjs.tiles.DMPedestalTile", "Reika.ChromatiCraft.TileEntity.AOE.TileEntityAccelerator", "com.sci.torcherino.tile.TileTorcherino", "com.sci.torcherino.tile.TileCompressedTorcherino","com.prex.prexmod.block.TileSpeedUpdateBlock"});
    //黑名单,加速火把一类的
    private int tww;//加速等级
    private long lastTime= System.currentTimeMillis();//反加速使用系统时间
    private AxisAlignedBB boundingBox;

    /**
     * 更新实体
     *
     */
    @Override
    public void updateEntity(){
        if(worldObj.isRemote)
            return;
        if(System.currentTimeMillis() - lastTime >= 45){
            if(boundingBox == null){
                tww=((SpeedUpdateBlock)worldObj.getBlock(xCoord,yCoord,zCoord)).tww;
                boundingBox=getEffectBounds();
            }
            lastTime = System.currentTimeMillis();
            speedUpTileEntities(worldObj,tww*20,boundingBox);
            speedUpRandomTicks(worldObj,tww*2,boundingBox);
        }//43毫秒1tick
    }
    private void speedUpRandomTicks(World world, int bonusTicks, AxisAlignedBB bBox)
    {
        if (bBox != null && bonusTicks != 0) {
            for (int x = (int)bBox.minX; x <= bBox.maxX; x++) {
                for (int y = (int)bBox.minY; y <= bBox.maxY; y++) {
                    for (int z = (int)bBox.minZ; z <= bBox.maxZ; z++) {
                        Block block = world.getBlock(x, y, z);
                        // 可以随机刻，除了流体
                        if (block.getTickRandomly()
                                && !(block instanceof BlockLiquid)
                                && !(block instanceof BlockFluidBase))
                        {
                            for (int i = 0; i < bonusTicks; i++) {
                                block.updateTick(world, x, y, z, world.rand);
                            }
                        }
                    }
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
    private void speedUpTileEntities(World world, int bonusTicks, AxisAlignedBB bBox) {
        //参数对应world,加速次数,范围
        if (bBox != null && bonusTicks != 0) {
            List<TileEntity> list = WorldHelper.getTileEntitiesWithinAABB(world, bBox);
            for (TileEntity tile : list) {
                if (!tile.isInvalid()
                        && !internalBlacklist.contains(tile.getClass().getName())
                        && !(tile instanceof TileSpeedUpdateBlock)
                        && !(tile instanceof TileSpeedUpdatePowerFlower)
                ){
                    for (int i = 0; i < bonusTicks; ++i) {
                        tile.updateEntity();
                    }
                }
            }
        }
    }

    public static void blacklist(Class<? extends TileEntity> clazz) {
        internalBlacklist.add(clazz.getName());
    }
}
