package com.prex.prexmod.mixin;

import com.prex.prexmod.emc.PrEmcMap;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.math.BigInteger;

@Mixin(EMCHelper.class)
public class ItemEMCH {


    @Inject(
            method="getEmcValue(Lnet/minecraft/item/ItemStack;)I",
            at=@At("HEAD"),
            cancellable=true,
            remap = false
    )
    private static void hook(
            ItemStack stack,
            CallbackInfoReturnable<Integer> cir){
        if(PrEmcMap.contains(stack)){
            BigInteger value = PrEmcMap.get(stack);
            if(value.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) >0) cir.setReturnValue(Integer.MAX_VALUE);
            else cir.setReturnValue(value.intValue());
        }
    }
}