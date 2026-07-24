package com.prex.prexmod.mixin;

import com.prex.prexmod.emc.PrEXEMC;
import com.prex.prexmod.emc.PrEmcMap;
import moze_intel.projecte.gameObjs.ObjHandler;
import moze_intel.projecte.gameObjs.container.TransmutationContainer;
import moze_intel.projecte.gameObjs.container.inventory.TransmutationInventory;
import moze_intel.projecte.utils.EMCHelper;
import moze_intel.projecte.utils.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.math.BigInteger;

@Mixin(value = TransmutationContainer.class)
public class ItemEMCT {
    @Shadow
    public TransmutationInventory transmutationInventory;
    @Inject(method = "func_82846_b",at=@At("HEAD"),cancellable = true,remap = false)
    public void func_82846_b(EntityPlayer player, int slotIndex, CallbackInfoReturnable<ItemStack> cir) {
        Slot slot = ((Container)(Object)this).getSlot(slotIndex);

        if (slot == null || !slot.getHasStack())
        {
            cir.setReturnValue(null);
            return;
        }

        ItemStack stack = slot.getStack();
        ItemStack newStack = stack.copy();
        EntityPlayer p= ((playerget)(Object)transmutationInventory).getPlayer();
        if(p==null){cir.setReturnValue(null);}
        if (slotIndex <= 7) //Input Slots
        {
            cir.setReturnValue(null);
            return;
        }
        else if (slotIndex >= 10 && slotIndex <= 25) // Output Slots
        {
            BigInteger emc = BigInteger.valueOf(EMCHelper.getEmcValue(newStack));
            if(PrEmcMap.contains(newStack))emc=PrEmcMap.get(newStack);
            int stackSize = 0;

            while ( PrEXEMC.get(p).compareTo(emc)>=0 && stackSize < newStack.getMaxStackSize() && ItemHelper.hasSpace(player.inventory.mainInventory, newStack))
            {
                PrEXEMC.add(p,emc.negate());//减少实际EMC
                transmutationInventory.emc=PrEXEMC.get(p).doubleValue();
                ItemHelper.pushStackInInv(player.inventory, ItemHelper.getNormalizedStack(newStack));//可能有漏洞
                stackSize++;
            }

            transmutationInventory.updateOutputs();
        }
        else if (slotIndex >= 26) //Unlearn Slot and Player Inventory
        {
            BigInteger emc = BigInteger.valueOf(EMCHelper.getEmcValue(newStack));
            if(PrEmcMap.contains(newStack))emc=PrEmcMap.get(newStack);//获取物品EMC

            if (emc.compareTo(BigInteger.ZERO) ==0 && stack.getItem() != ObjHandler.tome)//0判
            {
                cir.setReturnValue(null);
                return;
            }
            while(!transmutationInventory.hasMaxedEmc() && stack.stackSize > 0)
            {
                PrEXEMC.add(p,emc);//增加实际EMC
                transmutationInventory.emc=PrEXEMC.get(p).doubleValue();//增加gui EMC

//                transmutationInventory.addEmc(emc);
                --stack.stackSize;
            }

            transmutationInventory.handleKnowledge(newStack);

            if (stack.stackSize == 0)
            {
                slot.putStack(null);
            }
        }
        cir.setReturnValue(null);
    }
}
