package com.prex.prexmod.block;

import com.prex.prexmod.emc.PrEXEMC;
import moze_intel.projecte.gameObjs.container.inventory.TransmutationInventory;
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
    public int matter;
    public BigInteger storedEMC=BigInteger.ZERO;
    public int getmatter() {
        Block t= worldObj.getBlock(xCoord,yCoord,zCoord);
        for(PowerFlower q:PrExBlocks.power_flower){
            if(t.equals(q)){
                return q.matter;
            }
        }
        return 1;
    }
    @Override
    public void updateEntity(){


        if(worldObj.isRemote)
            return;

        tick++;//应用加速
        Block block =
                worldObj.getBlock(xCoord, yCoord, zCoord
                );
        this.matter=getmatter();
        if(tick%20==0){
            tick=0;
            if(block instanceof PowerFlower){


                PowerFlower flower = (PowerFlower)block;


                long gen =PowerFlower.gen[flower.matter];

                EntityPlayer player = worldObj.func_152378_a(this.owner);
                if(player!=null){
                    PrEXEMC.add(player, BigInteger.valueOf(gen));
                    if(!storedEMC.equals(BigInteger.ZERO)){
                        PrEXEMC.add(player, storedEMC);
                        storedEMC=BigInteger.ZERO;
                    }
                    Transmutation.sync(player);
                }
                else storedEMC = storedEMC.add(BigInteger.valueOf(gen));
                markDirty();

            }
        }
        markDirty();
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
        tag.setString("StoredEMC", storedEMC.toString());
        markDirty();
    }


    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        this. owner = UUID.fromString(tag.getString("Owner"));
        this. ownerName = tag.getString("OwnerName");
        this. tick = tag.getInteger("Tick");
        String s= tag.getString("StoredEMC");
        this. storedEMC= new BigInteger(s.isEmpty() ?"0":s);
    }

}