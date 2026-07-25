package com.prex.prexmod.mixin;

import com.prex.prexmod.QWQ;
import com.prex.prexmod.emc.PrEXEMC;
import moze_intel.projecte.gameObjs.container.inventory.TransmutationInventory;
import moze_intel.projecte.gameObjs.gui.GUITransmutation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.math.BigInteger;

import static com.prex.prexmod.emc.PrEXEMC.getEMCString;

//等价转化桌EMC渲染修改
@Mixin(GUITransmutation.class)
public class ProjectGUI {
    @Shadow
    TransmutationInventory inv;
    @ModifyArg(
            method = "func_146979_b",
            at= @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"),remap = false)
     protected String emc(String format){
        if(inv instanceof playerget){
            EntityPlayer player = ((playerget)inv).getPlayer();
            BigInteger cww=PrEXEMC.get(player);
            String formattedEmc = getEMCString(cww);
            if(QWQ.getRemcs(player).compareTo(BigInteger.ZERO)>0){
                formattedEmc=formattedEmc+" "+EnumChatFormatting.DARK_GREEN+"+"
                        +PrEXEMC.getEMCString(QWQ.getRemcs(player))+"/s";
            }else if(QWQ.getRemcs(player).compareTo(BigInteger.ZERO)<0){
                formattedEmc=formattedEmc+" "+EnumChatFormatting.DARK_RED
                        +PrEXEMC.getEMCString(QWQ.getRemcs(player))+"/s";
            }
            return "EMC:"+formattedEmc;
        }
        return "NO,ERROR";
    }

}
//@Mixin(GuiHandler.class)
//class GUIHandler{
//    @Inject(method = "getServerGuiElement",at = @At("HEAD"),remap = false)
//    private void bgui(int ID, EntityPlayer player, World world, int x, int y, int z, CallbackInfoReturnable<Object> cir){
//        TileEntity tile = world.getTileEntity(x, y, z);
//        if(ID==8){
//            if (tile != null){
//                if(tile instanceof CollectorMK3Tile) {
//                    cir.setReturnValue(new CollectorMK3Container(player.inventory, (CollectorMK3Tile)tile));
//                }
////                if(tile instanceof CollectorMK1Tile) {
////                    cir.setReturnValue(new CollectorMK3Container(player.inventory, (CollectorMK1Tile)tile));
////                }
//            }
//        }
//    }
//}
