package com.prex.prexmod.mixin;

import com.prex.prexmod.emc.NetworkHandler;
import com.prex.prexmod.emc.PrEmcMap;
import com.prex.prexmod.emc.PrEmcMapS;
import com.prex.prexmod.emc.PrExEmcMapFile;
import moze_intel.projecte.config.CustomEMCParser;
import moze_intel.projecte.network.commands.ReloadEmcCMD;
import net.minecraft.command.ICommandSender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*思路修改parseInteger部分，将超过上限的设为1,
然后在写入文件时再次判断是否超过Int上限,如果超过上限就写入PrexMap中，其余部分正常进行不修改*/
//@Mixin(SetEmcCMD.class)
//public class EmcCMD {
//    @Redirect(method = "func_71515_b",
//            at= @At(value = "INVOKE",
//                    target = "Lmoze_intel/projecte/config/CustomEMCParser;addToFile(Ljava/lang/String;II)Z")
//            ,remap = false)
//    public boolean setEmcCmd(String name, int meta, int s, @Local String[] params,@Local ICommandSender sender) {
//        if(s!=-1){
//            if(PrEmcMap.contains(name,meta)||PrExEmcMapFile.containsKey(name)){
//                PrExEmcMapFile.removeFromFile(name,meta);
//                return PrExEmcMapFile.addToFile(name,meta,BigInteger.valueOf(s)) & CustomEMCParser.addToFile(name, meta, s);
//            }else{
//                PrExEmcMapFile.removeFromFile(name,meta);
//                return CustomEMCParser.addToFile(name, meta, s);
//            }
//        }
//
//        if (params.length >= 1) {//异常处理
//            BigInteger emc;
//            try{
//            if (params.length == 1) {
//                emc = new BigInteger(params[0]);
//            } else {
//                boolean isOD = !name.contains(":");
//                if (!isOD) {
//                    if (params.length > 2) {
//                        emc=new BigInteger(params[2]);
//                    } else {
//                        emc=new BigInteger(params[1]);
//                    }
//                } else {
//                    emc=new BigInteger(params[1]);
//                }
//            }}catch(NumberFormatException e){
//                return false;
//            }
//            if (emc.compareTo(BigInteger.ZERO)<=0) {return false;}
//            if (emc.compareTo(BigDecimal.valueOf(Double.MAX_VALUE).toBigInteger())>=0) {
//                sender.addChatMessage(new ChatComponentTranslation("prex.command.set.max",emc));
//                return false;}
//            sender.addChatMessage(new ChatComponentTranslation("pe.command.set.success", name, emc+"Please ignore the error messages"));
//            return PrExEmcMapFile.addToFile(name,meta,emc) && CustomEMCParser.addToFile(name, meta, Integer.MAX_VALUE);
//        }
//        return false;
//    }
//}
//@Mixin(RemoveEmcCMD.class)
//class RemoveEmc{
//    @Redirect(method = "func_71515_b",at= @At(value = "INVOKE",
//            target = "Lmoze_intel/projecte/config/CustomEMCParser;addToFile(Ljava/lang/String;II)Z"),
//    remap = false)
//    public boolean removeEmc(String name, int meta, int s) {
//        if (PrEmcMap.contains(name,meta) || PrExEmcMapFile.PrExEmcMap.containsKey(name)) {
//            PrExEmcMapFile.removeFromFile(name,meta);
//            PrExEmcMapFile.addToFile(name,meta,BigInteger.ZERO);
//        }
//
//        return CustomEMCParser.addToFile(name, meta, 0);
//    }
//}
@Mixin(CustomEMCParser.class)
class EmcParser{
//    @Inject(method = "readUserData",at= @At(
//            value = "INVOKE",
//            target = "Ljava/util/Map;clear()V"),
//    remap = false)
//    private static void readUserData(CallbackInfo ci){
//        PrExEmcMapFile.readFile();
//    }
    @Inject(method = "removeFromFile",at= @At(value = "INVOKE",
            target = "Lmoze_intel/projecte/utils/FileHelper;closeStream(Ljava/io/Closeable;)V"),
    remap = false)
    private static void removeFromFile(String name, int meta, CallbackInfoReturnable<Boolean> cir){
        PrExEmcMapFile.removeFromFile(name,meta);
    }
}
@Mixin(ReloadEmcCMD.class)
class EmcReload{
    @Inject(method = "func_71515_b",at= @At(value = "INVOKE",
            target = "Lmoze_intel/projecte/config/CustomEMCParser;readUserData()V"),remap = false)
    public void function(ICommandSender sender, String[] params, CallbackInfo ci){
        PrExEmcMapFile.readFile();
    }
    @Inject(method = "func_71515_b",
            at= @At(value = "INVOKE", target = "Lmoze_intel/projecte/network/PacketHandler;sendFragmentedEmcPacketToAll()V"),
    remap = false)
    public void func(ICommandSender sender, String[] params, CallbackInfo ci){
        NetworkHandler.REmcMap.sendToAll(new PrEmcMapS(PrEmcMap.gets()));
    }
}