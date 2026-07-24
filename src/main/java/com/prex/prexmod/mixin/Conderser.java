package com.prex.prexmod.mixin;

import com.prex.prexmod.emc.PrEmcMap;
import cpw.mods.fml.common.FMLLog;
import moze_intel.projecte.gameObjs.container.CondenserMK2Container;
import moze_intel.projecte.gameObjs.container.slots.condenser.SlotCondenserInput;
import moze_intel.projecte.gameObjs.container.slots.condenser.SlotCondenserMK2Lock;
import moze_intel.projecte.gameObjs.tiles.CondenserTile;
import moze_intel.projecte.gameObjs.tiles.TileEmc;
import moze_intel.projecte.utils.EMCHelper;
import moze_intel.projecte.utils.ItemHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.math.BigDecimal;
import java.math.BigInteger;

@Mixin(value = CondenserTile.class,remap = false)
public abstract class Conderser extends TileEmc{
    @Shadow
    private ItemStack lock;
    @Shadow
    public int requiredEmc;
    @Shadow
    protected ItemStack[] inventory = new ItemStack[92];
    @Shadow
    protected abstract void pushStack();
    @Shadow
    protected abstract boolean hasSpace();
    @Shadow
    public abstract ItemStack func_70301_a(int slot);
    @Shadow
    protected abstract boolean isStackEqualToLock(ItemStack stack);
    @Shadow
    public abstract ItemStack func_70298_a(int slot, int qnt);
    @Shadow
    public int displayEmc;
    @Inject(method = "getProgressScaled",remap = false,at=@At(value = "RETURN",ordinal = 1),cancellable=true)
    private void twt(CallbackInfoReturnable<Integer> cir){
        BigInteger a=BigInteger.valueOf(requiredEmc);
        if(PrEmcMap.contains(lock))a=PrEmcMap.get(lock);
        BigInteger d=BigDecimal.valueOf(this.getStoredEmc()).toBigInteger();
        cir.setReturnValue((d.compareTo(a)>=0)? 102 : d.multiply(BigInteger.valueOf(102)).divide(a).intValue());
        cir.cancel();
    }
    @Inject(method = "condense",remap = false,at=@At("HEAD"), cancellable = true)
    private void qwq(CallbackInfo ci){//rEmcMap扣除与计算
        for (int i = 1; i < 92; i++)
        {
            ItemStack stack = func_70301_a(i);
            if (stack == null || isStackEqualToLock(stack)) {continue;}
            if (stack.stackSize <= 0) {inventory[i] = null;continue;}
            FMLLog.info("aaaa %s:%s",stack,lock);
            this.func_70298_a(i, 1);
            if(PrEmcMap.contains(stack)) this.addEMC(PrEmcMap.get(stack).doubleValue());
            else this.addEMC(EMCHelper.getEmcValue(stack));
            break;
        }
        if(this.requiredEmc!=0){
            if(PrEmcMap.contains(this.lock)){
                BigInteger a=PrEmcMap.get(this.lock);
                if(new BigDecimal(this.getStoredEmc()).toBigInteger().compareTo(a)>=0 && this.hasSpace()){
                    this.removeEMC(a.doubleValue());
                    this.pushStack();
                }
                ci.cancel();
                return;

            }
            if (this.getStoredEmc() >= (double)this.requiredEmc && this.hasSpace()) {
                this.removeEMC(this.requiredEmc);
                this.pushStack();
            }
        }

        ci.cancel();
    }
}
@Mixin(TileEmc.class)
class Te {//上限扩展
    @ModifyArg(method = "<init>()V",at= @At(value = "INVOKE", target = "Lmoze_intel/projecte/gameObjs/tiles/TileEmc;setMaximumEMC(D)V"))
    private static double init(double par1) {
        return Math.pow(10,40);
    }
}
@Mixin(SlotCondenserMK2Lock.class)
class tbb{//禁用MK2输入
    @Shadow
    private CondenserMK2Container container;

    @Inject(method = "func_75214_a",remap = false,at=@At("HEAD"),cancellable = true)
    private void setInput(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if (stack != null && EMCHelper.doesItemHaveEmc(stack)&&!PrEmcMap.contains(stack) && !this.container.tile.getWorldObj().isRemote) {
            ((Slot)(Object)this).putStack(ItemHelper.getNormalizedStack(stack));
            this.container.tile.checkLockAndUpdate();
            this.container.detectAndSendChanges();
        }
        cir.setReturnValue(false);
    }
}

