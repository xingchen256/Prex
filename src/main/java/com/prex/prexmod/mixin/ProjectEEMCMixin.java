package com.prex.prexmod.mixin;


import java.math.BigDecimal;
import java.math.BigInteger;

import com.prex.prexmod.IPrexEMC;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.nbt.NBTTagCompound;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import moze_intel.projecte.playerData.TransmutationProps;


@Mixin(TransmutationProps.class)
public class ProjectEEMCMixin implements IPrexEMC {


    @Unique
    private BigInteger prex$emc = BigInteger.ZERO;

    /*
     * 读取 EMC
     */
    @Inject(
            method = "getTransmutationEmc",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void getEMC(
            org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<Double> cir
    ){
        cir.setReturnValue(
                prex$emc.doubleValue()
        );
    }



    /*
     * 设置 EMC
     */
    @Inject(
            method = "setTransmutationEmc",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void setEmc(
            double value,
            CallbackInfo ci
    ){//同步EMC
        FMLLog.info("aValue = %s", value);

//        prex$emc =new BigInteger(Double.toString(value));
////                BigInteger.valueOf((long)value);

        ci.cancel();
    }
    /*
     * 保存世界
     */
    @Inject(
            method = "saveNBTData",
            at = @At("RETURN"),
            remap = false
    )
    private void save(
            NBTTagCompound compound,
            CallbackInfo ci
    ){

        NBTTagCompound tag =
                compound.getCompoundTag(
                        "ProjectETransmutation"
                );


        tag.setString(
                "PreX_EMC",
                prex$emc.toString()
        );

    }
    /*
     * 读取世界
     */
    @Inject(
            method = "loadNBTData",
            at = @At("RETURN"),
            remap = false
    )
    private void load(
            NBTTagCompound compound,
            CallbackInfo ci
    ){

        NBTTagCompound tag =
                compound.getCompoundTag(
                        "ProjectETransmutation"
                );


        if(tag.hasKey("PreX_EMC"))
        {
            prex$emc =
                    new BigInteger(
                            tag.getString("PreX_EMC")
                    );
        }else if(tag.getDouble("transmutationEmc")!=0){//如果之前没有加prex,将原Emc迁移到rEmc
            prex$emc= BigDecimal.valueOf(tag.getDouble("transmutationEmc")).toBigInteger();
        }

    }
    /*
     * 网络同步
     */
    @Inject(
            method = "saveForPacket",
            at = @At("RETURN"),
            remap = false
    )
    private void packetSave(
            org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<NBTTagCompound> cir
    ){

        cir.getReturnValue()
                .setString(
                        "PreX_EMC",
                        prex$emc.toString()
                );

    }
    /*
     * 客户端同步读取
     */
    @Inject(
            method = "readFromPacket",
            at = @At("RETURN"),
            remap = false
    )
    private void packetRead(
            NBTTagCompound compound,
            CallbackInfo ci
    ){

        if(compound.hasKey("PreX_EMC"))
        {
            prex$emc =
                    new BigInteger(
                            compound.getString("PreX_EMC")
                    );
        }

    }


    @Override
    public BigInteger prex$getEMC() {
        return prex$emc;
    }
    @Override
    public void prex$setEMC(BigInteger value) {
        prex$emc = value;
    }
}