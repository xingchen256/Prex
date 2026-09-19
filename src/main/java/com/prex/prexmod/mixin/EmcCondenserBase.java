package com.prex.prexmod.mixin;

import com.mordenkainen.equivalentenergistics.blocks.condenser.tiles.TileEMCCondenserBase;
import com.mordenkainen.equivalentenergistics.core.textures.TextureEnum;
import com.mordenkainen.equivalentenergistics.integration.IEMCHandler;
import com.mordenkainen.equivalentenergistics.integration.Integration;
import com.mordenkainen.equivalentenergistics.integration.ae2.EMCCraftingPattern;
import com.mordenkainen.equivalentenergistics.integration.projecte.ProjectE;
import com.mordenkainen.equivalentenergistics.items.ItemEMCCell;
import com.mordenkainen.equivalentenergistics.items.ItemEMCCrystal;
import com.mordenkainen.equivalentenergistics.items.ItemEnum;
import com.mordenkainen.equivalentenergistics.items.ItemStorageComponent;
import com.prex.prexmod.emc.PrEmcMap;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.math.BigDecimal;

import static com.prex.prexmod.ExampleMod.*;

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
@Mixin(value = ProjectE.class,remap = false)
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
    @Inject(method="setCrystalEMC",at=@At("RETURN"))//register注册不支持int的补丁
    public void teew(CallbackInfo ci){
        double[] CRYSTAL_VALUES;
        if (enableRecipe){
            CRYSTAL_VALUES= new double[]{1.0D,1024.0D, 67108864.0D, 281E12D, 3022E20D};
        }else{
            CRYSTAL_VALUES= new double[]{1.0D,256.0D, 1048576.0D, 17179E6D, 1125E12D};
        }
        for (int i=0;i<=4;i++){
            PrEmcMap.put(new ItemStack(ItemEnum.EMCCRYSTAL.getItem(), 1, i),
                    BigDecimal.valueOf(CRYSTAL_VALUES[i]).toBigInteger());
        }
    }
}
@Mixin(value = ItemEMCCrystal.class,remap = false)
class emccrystalMixin{
    @Shadow
    @Final
    @Mutable
    public static double[] CRYSTAL_VALUES;
    @Inject(method = "<init>",at=@At("HEAD"))
    private static void ttte(CallbackInfo ci){
        if (enableRecipe){
            CRYSTAL_VALUES= new double[]{1.0D,1024.0D, 67108864.0D, 281E12D, 3022E20D};
        }else{
            CRYSTAL_VALUES= new double[]{1.0D,256.0D, 1048576.0D, 17179E6D, 1125E12D};
        }
    }
}
@Mixin(value = ItemStorageComponent.class,remap = false)
class AddEmcTTT{
    @ModifyConstant(method = "<init>",constant = @Constant(intValue = 8))
    private static int ttte(int constant){
        return EECell;
    }
    public String func_77653_i(ItemStack stack) {
        return StatCollector.translateToLocalFormatted("item.prex.storage_component", stack.getItemDamage()+1);
    }
    @Inject(method = "func_77613_e",at=@At("HEAD"),cancellable = true,remap = false)
    private void ffff(ItemStack stack, CallbackInfoReturnable<EnumRarity> cir){
        cir.setReturnValue(EnumRarity.values()[(int)(stack.getItemDamage() / EECellN)]);
    }
    @Inject(method = "func_77617_a",at=@At("HEAD"),cancellable = true,remap = false)
    private void tttt(int damage, CallbackInfoReturnable<IIcon> cir){
        cir.setReturnValue(TextureEnum.EMCSTORAGECOMPONENT.getTexture((int)(damage/EECellP)));
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
        return EECell;
    }
    public String func_77653_i(ItemStack stack) {
        return StatCollector.translateToLocalFormatted("item.prex.storage_cell", stack.getItemDamage()+1);
    }
    @Inject(
            method = "<clinit>",
            at = @At("RETURN"),
            remap = false
    )
    private static void expandCapacities(CallbackInfo ci) {
        double[] capacities = new double[EECell];
        double[] drain = new double[EECell];

        for (int i=0;i<8;i++){
            capacities[i] = CAPACITIES[i];
            drain[i] = DRAIN[i];
        }
        for (int i=8;i<EECell;i++){
            capacities[i] = 6.5536E10*Math.pow(4,i-8);
            drain[i]=25.6*Math.pow(2,i-8);
        }
        DRAIN = drain;
        CAPACITIES = capacities;
    }
    @Inject(method = "func_77613_e",at=@At("HEAD"),cancellable = true,remap = false)
    private void ffff(ItemStack stack, CallbackInfoReturnable<EnumRarity> cir){
        cir.setReturnValue(EnumRarity.values()[(int)(stack.getItemDamage()/EECellN)]);
    }
    @Inject(method = "func_77617_a",at=@At("HEAD"),cancellable = true,remap = false)
    private void tttt(int damage, CallbackInfoReturnable<IIcon> cir){
        cir.setReturnValue(TextureEnum.EMCCELL.getTexture((int)(damage/EECellP)));
    }
}
