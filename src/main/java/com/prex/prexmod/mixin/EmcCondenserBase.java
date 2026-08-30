package com.prex.prexmod.mixin;

import com.mordenkainen.equivalentenergistics.blocks.condenser.tiles.TileEMCCondenserBase;
import com.mordenkainen.equivalentenergistics.integration.IEMCHandler;
import com.mordenkainen.equivalentenergistics.integration.Integration;
import com.mordenkainen.equivalentenergistics.integration.ae2.EMCCraftingPattern;
import com.mordenkainen.equivalentenergistics.integration.projecte.ProjectE;
import com.prex.prexmod.emc.PrEmcMap;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TileEMCCondenserBase.class)
public class EmcCondenserBase {
    @Redirect(method = "processItems",at= @At(
            value = "INVOKE",
            target = "Lcom/mordenkainen/equivalentenergistics/integration/IEMCHandler;getSingleEnergyValue(Lnet/minecraft/item/ItemStack;)D"),
    remap = false)
    public double pemc(IEMCHandler instance, ItemStack itemStack){
        if (PrEmcMap.contains(itemStack)) {
            return PrEmcMap.get(itemStack).doubleValue();
        }else{
            return Integration.emcHandler.getSingleEnergyValue(itemStack);
        }
    }
}
@Mixin(EMCCraftingPattern.class)
class EMCCraftingPatternMixin{
    @Redirect(method = "createItemPattern",at= @At(value = "INVOKE",
            target = "Lcom/mordenkainen/equivalentenergistics/integration/IEMCHandler;getSingleEnergyValue(Lnet/minecraft/item/ItemStack;)D"),
    remap = false)
    public double pemc(IEMCHandler instance, ItemStack itemStack){
        if (PrEmcMap.contains(itemStack)) {
            return PrEmcMap.get(itemStack).doubleValue();
        }else{
            return Integration.emcHandler.getSingleEnergyValue(itemStack);
        }
    }
}
@Mixin(ProjectE.class)
class ProjectEMixin{
    @Inject(method = "hasEMC",at=@At("HEAD"),cancellable = true,remap = false)
    public void tt(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(EMCHelper.getEmcValue(itemStack) > 0);
        return ;
    }//针对GTNH版的修改
    @Inject(method = "getEnergyValue",at=@At("HEAD"),cancellable = true,remap = false)
    public void  tw(ItemStack itemStack, CallbackInfoReturnable<Double> cir){
        cir.setReturnValue((double)EMCHelper.getEmcValue(itemStack));
    }
}
