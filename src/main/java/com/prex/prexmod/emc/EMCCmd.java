package com.prex.prexmod.emc;

import moze_intel.projecte.config.CustomEMCParser;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;

import java.math.BigDecimal;
import java.math.BigInteger;

public class EMCCmd extends CommandBase {
    @Override
    public String getCommandName() {
        return "prex";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        iCommandSender.addChatMessage(new ChatComponentTranslation("prex.command.ttth"));
        return "";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        EntityPlayer p=(EntityPlayer)iCommandSender;
        ItemStack h=p.getHeldItem();
        if(h == null){
            iCommandSender.addChatMessage(new ChatComponentTranslation("prex.command.ph"));
        }else{
            String dp=h.getDisplayName();
            String du= Item.itemRegistry.getNameForObject(h.getItem());
            int meta=h.getItemDamage();
            if(strings.length==2){
                if(strings[0].equals("setEmc")){}
                BigInteger emc;
                try{
                    emc=new BigInteger(strings[1]);
                }catch(NumberFormatException e){
                    iCommandSender.addChatMessage(new ChatComponentTranslation("prex.command.set.num"));
                    return;
                }
                if (emc.compareTo(BigInteger.valueOf(Integer.MAX_VALUE))<=0) {
                    iCommandSender.addChatMessage(new ChatComponentTranslation("prex.command.set.min"));
                }else if(emc.compareTo(BigDecimal.valueOf(Double.MAX_VALUE).toBigInteger())>=0){
                    iCommandSender.addChatMessage(new ChatComponentTranslation("prex.command.set.max"));
                }else{
                    PrExEmcMapFile.addToFile(du,meta,emc);
                    CustomEMCParser.addToFile(du,meta, Integer.MAX_VALUE);
                    iCommandSender.addChatMessage(new ChatComponentTranslation("prex.command.setemc",dp,emc));
                }
                return;
            }else if(strings.length==1){

                switch (strings[0]) {
                    case "restEmc":
                        PrExEmcMapFile.removeFromFile(du, meta);
                        CustomEMCParser.removeFromFile(du,meta);
                        iCommandSender.addChatMessage(new ChatComponentTranslation("prex.command.rest",dp));
                        break;
                    case "removeEmc":
                        if(PrEmcMap.contains(du, meta) || PrExEmcMapFile.containsKey(du,meta)){
                            PrExEmcMapFile.addToFile(du, meta, BigInteger.ZERO);
                            CustomEMCParser.addToFile(du,meta,0);
                            iCommandSender.addChatMessage(new ChatComponentTranslation("prex.command.remove",dp));
                        }else{
                            iCommandSender.addChatMessage(new ChatComponentTranslation("prex.command.unknow",dp));
                        }

                        break;
                    default:
                        iCommandSender.addChatMessage(new ChatComponentTranslation("prex.command.ttth"));
                        break;
                }
                return;
            }
            iCommandSender.addChatMessage(new ChatComponentTranslation("prex.command.ttth"));
        }
    }
}
