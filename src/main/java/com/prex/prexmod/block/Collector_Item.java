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
import java.util.List;

public class Collector_Item extends ItemBlock {

    public Collector_Item(Block p_i45328_1_) {
        super(p_i45328_1_);
    }

    @Override
    public void addInformation(ItemStack s, EntityPlayer p, List l, boolean p_77624_4_) {
        for(int i=0;i<PrExBlocks.collector.length;i++){
            if(Item.getItemFromBlock(PrExBlocks.collector[i])==s.getItem()){
                if(i<3){
                    l.add(EnumChatFormatting.DARK_PURPLE+ StatCollector.translateToLocalFormatted("prex.tile.powerflower_tips")+EnumChatFormatting.BLUE+Math.pow(2,i+2));
                    if (i<=2)
                        l.add(EnumChatFormatting.DARK_PURPLE+StatCollector.translateToLocalFormatted("prex.tile.collector_store",
                                EnumChatFormatting.BLUE+String.valueOf(5000*(i+1)*(i+2))));
                }else{
                    l.add(EnumChatFormatting.DARK_PURPLE+StatCollector.translateToLocalFormatted("prex.tile.powerflower_tips")+EnumChatFormatting.GREEN+
                            PrEXEMC.getEMCString(BigDecimal.valueOf(Math.pow(2,i+4)*10).toBigInteger())
                            +"emc/s");
                    l.add(EnumChatFormatting.DARK_PURPLE+StatCollector.translateToLocalFormatted("prex.tile.collector_store",((i!=15)?EnumChatFormatting.BLUE:EnumChatFormatting.DARK_RED)+
                            PrEXEMC.getEMCString(BigDecimal.valueOf(Math.pow(2,i-3)*100000).toBigInteger())+"emc"));
                }

            }
        }
        super.addInformation(s, p, l, p_77624_4_);
    }
}
