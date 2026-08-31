package com.prex.prexmod.mixin;

import com.prex.prexmod.emc.PrEmcMap;
import com.prex.prexmod.emc.PrEmcMapV;
import com.prex.prexmod.emc.PrExEmcMapFile;
import moze_intel.projecte.emc.NormalizedSimpleStack;
import moze_intel.projecte.emc.SimpleGraphMapper;
import moze_intel.projecte.emc.SimpleStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
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
public abstract class RecipeItemEmc{
    @Inject(
            method = "valueForConversion",
            at = @At(value = "INVOKE",
                    target = "Lmoze_intel/projecte/utils/PELogger;logWarn(Ljava/lang/String;)V",
                    ordinal = 0),
            cancellable = true
    )
    public void tww(Map values,@Coerce Object conversion,CallbackInfoReturnable cir){
        cir.setReturnValue(tw(values,conversion));
    }
    public Fraction tw(Map values, @Coerce Object conversion){
        BigInteger result =
                PrEmcMapV.valueForConversion(values,conversion);
        if(result.compareTo(BigInteger.ZERO)==0){
            return Fraction.getFraction(0,1);
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
            if (output instanceof NormalizedSimpleStack.NSSItem) {
                NormalizedSimpleStack.NSSItem stack = (NormalizedSimpleStack.NSSItem) output;
                if(!PrExEmcMapFile.containsKey(stack.itemName,stack.damage))//这个等价在重设价格后会重计算然后.....
                    PrEmcMap.put(new SimpleStack(new ItemStack((Item)Item.itemRegistry.getObject(stack.itemName),1,stack.damage)),result );
            }else{
                NormalizedSimpleStack.NSSOreDictionary ore =
                        (NormalizedSimpleStack.NSSOreDictionary) output;
                String oreName = ore.od;
                for (ItemStack stack : OreDictionary.getOres(oreName)) {

                    if (stack == null || stack.getItem() == null)
                        continue;
                    String itemName = Item.itemRegistry.getNameForObject(stack.getItem());
                    if (itemName == null) {continue;}
                    int damage = stack.getItemDamage();
                    if (!PrExEmcMapFile.containsKey(itemName, damage)) {
                        PrEmcMap.put(new SimpleStack(new ItemStack(stack.getItem(), 1, damage)), result);
                    }
                }
            }
                    } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
//        FMLLog.warning("FQWW Conversion result: "+ conversion +result);
        return Fraction.getFraction(Integer.MAX_VALUE,1);

    }
}
