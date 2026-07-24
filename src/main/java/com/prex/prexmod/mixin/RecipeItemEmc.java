package com.prex.prexmod.mixin;

import com.prex.prexmod.emc.PrEmcMap;
import com.prex.prexmod.emc.PrEmcMapV;
import moze_intel.projecte.emc.NormalizedSimpleStack;
import moze_intel.projecte.emc.SimpleGraphMapper;
import moze_intel.projecte.emc.SimpleStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Map;

@Mixin(value = SimpleGraphMapper.class,remap = false)
public  class RecipeItemEmc{

    @Inject(
            method = "valueForConversion",
            at = @At(value = "INVOKE",
                    target = "Lmoze_intel/projecte/utils/PELogger;logWarn(Ljava/lang/String;)V",
                    ordinal = 0),
            cancellable = true
    )
    public void tw(       Map values,
                           @Coerce Object conversion,
                           CallbackInfoReturnable cir){
        BigInteger result =
                PrEmcMapV.valueForConversion(values,conversion);
        if(result.compareTo(BigInteger.ZERO)==0){
            cir.setReturnValue(Fraction.getFraction(0,1));
            return;
        }
        try {
            Field outputField =  conversion.getClass().getDeclaredField("output");
            outputField.setAccessible(true);
            Object output;
            try {
                output = outputField.get(conversion);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            NormalizedSimpleStack.NSSItem stack = (NormalizedSimpleStack.NSSItem) output;
            PrEmcMap.put(new SimpleStack(new ItemStack((Item)Item.itemRegistry.getObject(stack.itemName),1,stack.damage)),result );
//            FMLLog.info(stack.itemName+String.format(": %s",result));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
//        FMLLog.warning("FQWW Conversion result: "+ conversion +result);
        cir.setReturnValue(Fraction.getFraction(Integer.MAX_VALUE,1));

    }


}
