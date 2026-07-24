package com.prex.prexmod.mixin;

import com.prex.prexmod.QWQ;
import com.prex.prexmod.emc.PrEXEMC;
import moze_intel.projecte.gameObjs.container.CollectorMK3Container;
import moze_intel.projecte.gameObjs.container.inventory.TransmutationInventory;
import moze_intel.projecte.gameObjs.gui.GUITransmutation;
import moze_intel.projecte.gameObjs.tiles.CollectorMK1Tile;
import moze_intel.projecte.gameObjs.tiles.CollectorMK3Tile;
import moze_intel.projecte.utils.GuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
            if(PrEXEMC.getRemcs(player).compareTo(BigInteger.ZERO)>0){
                formattedEmc=formattedEmc+" "+EnumChatFormatting.DARK_GREEN+"+"
                        +PrEXEMC.getEMCString(PrEXEMC.getRemcs(player))+"/s";
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
