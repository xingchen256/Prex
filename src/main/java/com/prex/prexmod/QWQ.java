package com.prex.prexmod;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.math.BigInteger;

public class QWQ implements IExtendedEntityProperties {
    public static String happy="ProjectReExtendedExchange";
    private static BigInteger rEmcs=BigInteger.ZERO;
    public static BigInteger getRemcs(){
        return rEmcs;
    }
    public static void setRemcs(BigInteger b){
        rEmcs=b;
    }

    @Override
    public void saveNBTData(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setString("RSEMC", rEmcs.toString());
    }

    @Override
    public void loadNBTData(NBTTagCompound nbtTagCompound) {
        if(nbtTagCompound.hasKey("RSEMC")){
            rEmcs=new BigInteger(nbtTagCompound.getString("RSEMC"));
        }
    }

    @Override
    public void init(Entity entity, World world) {
    }
}

