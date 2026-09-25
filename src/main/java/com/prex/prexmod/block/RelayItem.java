package com.prex.prexmod.block;

import com.prex.prexmod.emc.PrEXEMC;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class RelayItem extends ItemBlock {

    public RelayItem(Block p_i45328_1_) {
        super(p_i45328_1_);
    }

    @Override
    public void addInformation(ItemStack s, EntityPlayer p, List l, boolean p_77624_4_) {
        for(int i=0;i<PrExBlocks.basicRelays.length;i++){
            if(Item.getItemFromBlock(PrExBlocks.basicRelays[i])==s.getItem()){
                if(i<3){
                    l.add(EnumChatFormatting.DARK_PURPLE+ StatCollector.translateToLocalFormatted(
                            "prex.tile.relay.output",EnumChatFormatting.BLUE+String.valueOf ((160*i*i)-32*i+64)));
                    l.add(EnumChatFormatting.DARK_PURPLE+StatCollector.translateToLocalFormatted(
                            "prex.tile.collector_store",EnumChatFormatting.BLUE+String.valueOf((int)(10000*Math.pow(10,i)))));
                }else{
                    l.add(EnumChatFormatting.DARK_PURPLE+ StatCollector.translateToLocalFormatted(
                            "prex.tile.relay.output",EnumChatFormatting.BLUE+
                            PrEXEMC.getEMCString(BigInteger.valueOf(64*(long)Math.pow(i+1,2)))));
                    l.add(EnumChatFormatting.DARK_PURPLE+StatCollector.translateToLocalFormatted(
                            "prex.tile.collector_store",((i!=15)?EnumChatFormatting.BLUE:EnumChatFormatting.DARK_RED)+
                            PrEXEMC.getEMCString(BigDecimal.valueOf(Math.pow(i-2,2)*1000_0000).toBigInteger())));
                }

            }
        }
        super.addInformation(s, p, l, p_77624_4_);
    }
}
