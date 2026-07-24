package com.prex.prexmod.mixin;

import com.prex.prexmod.emc.PrEmcMap;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import java.math.BigInteger;
import java.util.List;

import static com.prex.prexmod.emc.PrEXEMC.getEMCString;

@Mixin(targets = "moze_intel.projecte.events.ToolTipEvent",remap = false)
public class ItemTips {
    @Redirect(
            method = "tTipEvent",
            at = @At(
                    value = "INVOKE",
                    target =
                            "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 0
            ),
            remap = false
    )
    public String qw1(String format, Object[] args,ItemTooltipEvent event) {
        String qEmc= getEMCString(BigInteger.valueOf(EMCHelper.getEmcValue(event.itemStack)));
        if(PrEmcMap.contains(event.itemStack))qEmc= getEMCString(PrEmcMap.get(event.itemStack));
        return qEmc;
    }

    @Redirect(
            method = "tTipEvent",
            at = @At(
                    value = "INVOKE",
                    target =
                            "Ljava/util/List;add(Ljava/lang/Object;)Z",ordinal = 12
            ),

            remap = false
    )
    public boolean qw2(List list, Object obj,ItemTooltipEvent event) {
        String qEmc= getEMCString(BigInteger.valueOf(EMCHelper.getEmcValue(event.itemStack)).multiply(BigInteger.valueOf(event.itemStack.stackSize)));
        if(PrEmcMap.contains(event.itemStack)) qEmc= getEMCString(PrEmcMap.get(event.itemStack).multiply(BigInteger.valueOf(event.itemStack.stackSize)));
        list.add(
                EnumChatFormatting.YELLOW
                        + StatCollector.translateToLocal("pe.emc.stackemc_tooltip_prefix")
                        + " "
                        + EnumChatFormatting.WHITE
                        + qEmc
        );
        return true;
    }
    @ModifyConstant(method = "tTipEvent",constant = @Constant(longValue =2147483647L),remap = false)
    public long qwer(long constant) {
        return 9223372036854775807L;
        //    19586883243712675840
    }
}
