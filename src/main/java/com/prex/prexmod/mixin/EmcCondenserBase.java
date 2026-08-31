package com.prex.prexmod.mixin;

import com.mordenkainen.equivalentenergistics.blocks.condenser.tiles.TileEMCCondenserBase;
import com.mordenkainen.equivalentenergistics.core.textures.TextureEnum;
import com.mordenkainen.equivalentenergistics.integration.IEMCHandler;
import com.mordenkainen.equivalentenergistics.integration.Integration;
import com.mordenkainen.equivalentenergistics.integration.ae2.EMCCraftingPattern;
import com.mordenkainen.equivalentenergistics.integration.projecte.ProjectE;
import com.mordenkainen.equivalentenergistics.items.ItemEMCCell;
import com.prex.prexmod.emc.PrEmcMap;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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
@Mixin(value = ItemEMCCell.class,remap = false)
class AddEmcCellMixin{
    @Shadow
    @Final
    @Mutable
    private static double[] CAPACITIES;
    @Shadow
    @Final
    @Mutable
    private static double[] DRAIN;
    @ModifyConstant(method = "<init>",constant = @Constant(intValue = 8))
    private static int twww(int constant){
        return 14;
    }
    @Inject(
            method = "<clinit>",
            at = @At("RETURN"),
            remap = false
    )
    private static void expandCapacities(CallbackInfo ci) {
        double[] capacities = new double[14];
        double[] drain = new double[14];

        for (int i=0;i<8;i++){
            capacities[i] = CAPACITIES[i];
            drain[i] = DRAIN[i];
        }
        capacities[8]=6.5536E10;
        capacities[9]=2.6215E11;
        capacities[10]=1.0486E12;
        capacities[11]=4.1943E12;
        capacities[12]=1.6777E13;
        capacities[13]=6.7108E13;
        drain[8]=25.6;
        drain[9]=51.2;
        drain[10]=102.4;
        drain[11]=204.8;
        drain[12]=409.6;
        drain[13]=819.2;
        DRAIN = drain;
        CAPACITIES = capacities;
    }
    @Inject(method = "func_77613_e",at=@At("HEAD"),cancellable = true,remap = false)
    private void ffff(ItemStack stack, CallbackInfoReturnable<EnumRarity> cir){
        cir.setReturnValue(EnumRarity.values()[(stack.getItemDamage()+2) / 5]);
    }
    @Inject(method = "func_77617_a",at=@At("HEAD"),cancellable = true,remap = false)
    private void tttt(int damage, CallbackInfoReturnable<IIcon> cir){
        cir.setReturnValue(TextureEnum.EMCCELL.getTexture((damage+1)/2));
    }
}
