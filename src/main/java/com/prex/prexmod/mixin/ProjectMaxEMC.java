package com.prex.prexmod.mixin;

import com.google.common.collect.Lists;
import com.llamalad7.mixinextras.sugar.Local;
import com.prex.prexmod.block.PrExBlocks;
import com.prex.prexmod.emc.PrEXEMC;
import com.prex.prexmod.emc.PrEmcMap;
import com.prex.prexmod.item.PrExItems;
import cpw.mods.fml.common.FMLLog;
import moze_intel.projecte.emc.SimpleStack;
import moze_intel.projecte.gameObjs.container.inventory.TransmutationInventory;
import moze_intel.projecte.playerData.Transmutation;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

//提升EMC上限取消限制,注意EMC不能超过1.79769313486231570E+308,精度会有BigInter来控制
@Mixin(value = TransmutationInventory.class)
public class ProjectMaxEMC {

    @Shadow
    public double emc;
    @Shadow
    private EntityPlayer player;
    @Shadow
    private ItemStack[] inventory = new ItemStack[27];
    @Inject(
            method = "hasMaxedEmc",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )private void qwq(CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(false);//无上限
    }
    @Inject(
            method = "addEmc",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )private void twt(double value, CallbackInfo ci){
        PrEXEMC.add(this.player, BigDecimal.valueOf(value).toBigInteger());
        this.emc=PrEXEMC.get(this.player).doubleValue();
        ci.cancel();//覆盖限制
    }
    @Inject(method = "removeEmc",at=@At("HEAD"),cancellable = true,remap = false)//我也不知道留着还有啥用了
    private void twa(double value,CallbackInfo ci){
        FMLLog.info("aaaaaFuck");
        PrEXEMC.add(this.player, BigDecimal.valueOf(-value).toBigInteger());
        this.emc=PrEXEMC.get(this.player).doubleValue();
//        FMLLog.info("qqq"+value);
//        this.emc -= value;
        if (PrEXEMC.get(this.player).compareTo(BigInteger.ZERO) < 0) {
            this.emc = (double)0.0F;
            PrEXEMC.set(this.player,0);
        }
        ci.cancel();
    }
    @ModifyArg(method = "updateOutputs(Z)V",
            at= @At(value = "INVOKE",
                    target = "Ljava/util/Collections;sort(Ljava/util/List;Ljava/util/Comparator;)V"),index = 1,remap = false)
    private Comparator<ItemStack> sort(Comparator<ItemStack> c){
        return PrEmcMap.EMCDDD;
    }
    @Redirect(
            method = "updateOutputs(Z)V",
            at = @At(
                    value = "FIELD",
                    target = "Lmoze_intel/projecte/gameObjs/container/inventory/TransmutationInventory;emc:D",
                    ordinal = 1
            ),
            remap = false
    )
    private double qwww(//物品显示条件增加
            TransmutationInventory instance, @Local Iterator<ItemStack> iter, @Local(ordinal = 1) ItemStack stack
    )
    {
        if(PrEmcMap.contains(stack)){
            if(PrEmcMap.get(stack).compareTo(PrEXEMC.get(this.player)) > 0) return 0;
            return instance.emc;
        }
        return instance.emc;
    }

    @Inject(method ="func_70305_f",at=@At("HEAD"),cancellable = true,remap = false)//不要鸟这个报错如果有
    private void f(CallbackInfo ci){//截停emc同步,避免一些异常
        if (!this.player.worldObj.isRemote) {
//            Transmutation.setEmc(this.player, this.emc);
            Transmutation.setInputsAndLocks((ItemStack[]) Arrays.copyOfRange(this.inventory, 0, 9), this.player);
            Transmutation.sync(this.player);
        }
        ci.cancel();
    }
}
@Mixin(targets = "moze_intel.projecte.emc.FuelMapper")
class FuelMap{
    @Shadow
    private static final List<SimpleStack> FUEL_MAP = Lists.newArrayList();
    @Inject(method = "loadMap",
            at= @At(value = "INVOKE",
                    target = "Ljava/util/Collections;sort(Ljava/util/List;Ljava/util/Comparator;)V"),
    remap = false)
    private static void addtomap(CallbackInfo ci) {
        for (Item a : PrExItems.fule) FUEL_MAP.add(new SimpleStack(new ItemStack(a, 1)));
        for (Block a : PrExBlocks.fuels) FUEL_MAP.add(new SimpleStack(new ItemStack(Item.getItemFromBlock(a), 1)));
    }
}
