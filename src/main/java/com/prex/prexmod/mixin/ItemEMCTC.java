package com.prex.prexmod.mixin;

import com.prex.prexmod.emc.PrEXEMC;
import com.prex.prexmod.emc.PrEmcMap;
import com.prex.prexmod.item.PrExItems;
import cpw.mods.fml.common.FMLLog;
import moze_intel.projecte.gameObjs.container.inventory.TransmutationInventory;
import moze_intel.projecte.gameObjs.container.slots.transmutation.SlotConsume;
import moze_intel.projecte.gameObjs.container.slots.transmutation.SlotInput;
import moze_intel.projecte.gameObjs.container.slots.transmutation.SlotLock;
import moze_intel.projecte.gameObjs.container.slots.transmutation.SlotOutput;
import moze_intel.projecte.utils.EMCHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.math.BigDecimal;
import java.math.BigInteger;

@Mixin(SlotOutput.class)
public class ItemEMCTC{
    @Shadow
    private TransmutationInventory inv;

    @Inject(method = "func_75209_a",at=@At("HEAD"),cancellable=true,remap = false)
    public void func_75209_a(int amount, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack stack = ((SlotOutput)(Object)this).getStack().copy();
        stack.stackSize = amount;
        BigInteger emcValue=BigInteger.valueOf(amount).multiply(BigInteger.valueOf(EMCHelper.getEmcValue(stack)));
        if(PrEmcMap.contains(stack))emcValue=BigInteger.valueOf(amount).multiply(PrEmcMap.get(stack));
        EntityPlayer p=((playerget)inv).getPlayer();
        if (emcValue.compareTo(PrEXEMC.get(p)) > 0) {
            stack.stackSize = 0;
            cir.setReturnValue(stack);
        } else {
            PrEXEMC.add (p,emcValue.negate());
            this.inv.emc=PrEXEMC.get(p).doubleValue();
            this.inv.checkForUpdates();
            this.inv.updateOutputs();
            cir.setReturnValue(stack);
        }
        cir.cancel();
    }
}
@Mixin(SlotConsume.class)
class ItemConsume {
    @Shadow
    private TransmutationInventory inv;
   @Inject(method = "func_75215_d",at=@At("HEAD"),cancellable = true,remap = false)
    public void func_75215_d(ItemStack stack, CallbackInfo ci) {
       if (stack != null) {
           ItemStack cache = stack.copy();
            BigInteger toAdd;
           for(toAdd = BigInteger.ZERO; !this.inv.hasMaxedEmc() && stack.stackSize > 0; --stack.stackSize) {
               if(PrEmcMap.contains(stack))toAdd= toAdd.add(PrEmcMap.get(stack).add(BigDecimal.valueOf(EMCHelper.getStoredEMCBonus(stack)).toBigInteger()));
               else toAdd=toAdd.add(BigInteger.valueOf(EMCHelper.getEmcValue(stack)));
           }
           PrEXEMC.add (((playerget)inv).getPlayer(),toAdd);
            this.inv.emc=PrEXEMC.get(((playerget)inv).getPlayer()).doubleValue();

           ((Slot)(Object)this).onSlotChanged();
           this.inv.handleKnowledge(cache);
           this.inv.updateOutputs();
       }
       ci.cancel();
   }
}

@Mixin(SlotInput.class)
class ItemInput{
    @Shadow
    private TransmutationInventory inv;
    @ModifyArg(method = "func_75215_d",
            at= @At(value = "INVOKE",
                    target = "Lmoze_intel/projecte/api/item/IItemEmc;addEmc(Lnet/minecraft/item/ItemStack;D)D",
                    ordinal = 1),
            index = 1,remap = false)
    private double setEmc0(double value){
        PrEXEMC.set(((playerget)inv).getPlayer(), BigInteger.ZERO);
        inv.emc=PrEXEMC.get(((playerget)inv).getPlayer()).doubleValue();
        return value;
    }
    @Inject(method = "func_75215_d",at= @At("HEAD"),
            remap = false)
    private void tw(ItemStack stack, CallbackInfo ci){//终极之心判断
        if(stack!=null){
            if(stack.getItem()==PrExItems.finalStar) {
                PrEXEMC.set(((playerget)inv).getPlayer(),0);
                inv.emc=PrEXEMC.get(((playerget)inv).getPlayer()).doubleValue();
            }
        }

    }
}
@Mixin(SlotLock.class)
class ItemLock{
    @Shadow
    private TransmutationInventory inv;
    @Inject(method = "func_75215_d",at=@At("HEAD"),remap = false)
    private void tw(ItemStack stack, CallbackInfo ci){
        if(stack!=null){
            if(stack.getItem()==PrExItems.finalStar) {
                PrEXEMC.add(((playerget)inv).getPlayer(),new BigInteger("2000000000000000000"));//200兆
                inv.emc=PrEXEMC.get(((playerget)inv).getPlayer()).doubleValue();
            }
        }
    }
}
