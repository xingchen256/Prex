package com.prex.prexmod.block;

import com.prex.prexmod.QWQ;
import com.prex.prexmod.emc.PrEXEMC;
import moze_intel.projecte.playerData.Transmutation;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.math.BigInteger;
import java.util.UUID;

import static com.prex.prexmod.ExampleMod.LOG;

public class TilePowerFlower extends TileEntity {
    public UUID owner = new UUID(0,0);
    public String ownerName="";
    public int tick=0;
    public int matter=0;
    private boolean a;
    private BigInteger gen;
    private long time=Minecraft.getSystemTime();
    private Block t;
//移除EMC存储
    public int getmatter() {
        for(PowerFlower q:PrExBlocks.power_flower){
            Block a=this.getBlockType();
            if(a.equals(q))return q.matter;
        }
        return 1;
    }
    @Override
    public void updateEntity(){
        if(worldObj.isRemote) return;
        tick++;//应用加速
        if(tick%20==0){
            tick=0;
            if(t==null) this.t=this.getBlockType();
            if(matter==0) {
                matter=getmatter();//避免重复获取
                gen=BigInteger.valueOf( PowerFlower.gen[matter]);
            }
            if(t instanceof PowerFlower){
                EntityPlayer player = worldObj.func_152378_a(this.owner);//通过uuid获取EntityPlayer
                if(player!=null){
                    if(!a){
                        QWQ.addRemcs(player,gen);
                        QWQ.sync(player);
                        a=true;
                    }
                    PrEXEMC.add(player, gen);
                    if(Minecraft.getSystemTime()-time>=2000){
                        Transmutation.sync(player);
                        time=Minecraft.getSystemTime();
                        markDirty();
                    }
                }else {a=false;}
            }
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
        EntityPlayer player = worldObj.func_152378_a(this.owner);
        if(a){
           QWQ.addRemcs(player,gen.negate());
           QWQ.sync(player);
        }
    }

    public String getOwerName(){
        if(this.ownerName.isEmpty()){
            LOG.warn("No power flower owner name set");
        }
        return this.ownerName;
    }
    public void setOwerName(String name){
        this.ownerName=name;
    }
    public void setOwnerUUID(UUID uuid){
        this.owner = uuid;
    }
    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        tag.setString("Owner", owner.toString());
        tag.setString("OwnerName", ownerName);
        tag.setInteger("Tick", tick);
        markDirty();
    }
    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        this. owner = UUID.fromString(tag.getString("Owner"));
        this. ownerName = tag.getString("OwnerName");
        this. tick = tag.getInteger("Tick");
    }

}