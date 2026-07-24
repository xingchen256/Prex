package com.prex.prexmod.mixin;

import com.prex.prexmod.emc.PrEmcMap;
import moze_intel.projecte.emc.EMCMapper;
import moze_intel.projecte.emc.SimpleStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.math.BigInteger;
import java.util.Map;

@Mixin(EMCMapper.class)
public class ItemEMCM {

    @Shadow
    public static Map<SimpleStack,Integer> emc;

    @Inject(
            method="getEmcValue",
            at=@At("HEAD"),
            cancellable=true,
            remap = false
    )
    private static void getValue(
            SimpleStack stack,
            CallbackInfoReturnable<Integer> cir
    ){

        SimpleStack copy = stack.copy();
        copy.qnty = 1;

        if(PrEmcMap.contains(copy)){
            BigInteger value = PrEmcMap.get(copy);
            if(value.compareTo(
                    BigInteger.valueOf(Integer.MAX_VALUE)
            )>0){
                cir.setReturnValue(
                        Integer.MAX_VALUE
                );
            }
            else{
                cir.setReturnValue(value.intValue());
            }
        }

    }



    @Inject(
            method="mapContains",
            at=@At("HEAD"),
            cancellable=true,
            remap = false
    )
    private static void contains(
            SimpleStack stack,
            CallbackInfoReturnable<Boolean> cir
    ){

        SimpleStack copy=stack.copy();
        copy.qnty=1;


        if(PrEmcMap.contains(copy))
        {
            cir.setReturnValue(true);
        }

    }


}
