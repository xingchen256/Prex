package com.prex.prexmod.block;

import com.prex.prexmod.emc.PrEXEMC;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.math.BigDecimal;
import java.math.BigInteger;
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
                    l.add(EnumChatFormatting.DARK_PURPLE+"最高效率:"+EnumChatFormatting.BLUE+Math.pow(2,i+2));
                    switch (i){
                        case 0:{
                            l.add(EnumChatFormatting.DARK_PURPLE+"存储上限:"+EnumChatFormatting.BLUE+10000);
                            break;
                        }
                        case 1:{l.add(EnumChatFormatting.DARK_PURPLE+"存储上限:"+EnumChatFormatting.BLUE+30000);
                        break;}
                        case 2:{l.add(EnumChatFormatting.DARK_PURPLE+"存储上限:"+EnumChatFormatting.BLUE+60000);break;}
                    }

                }else{
                    l.add(EnumChatFormatting.DARK_PURPLE+"最高效率:"+EnumChatFormatting.GREEN+
                            PrEXEMC.getEMCString(BigDecimal.valueOf(Math.pow(2,i+4)*10).toBigInteger())
                            +"emc/s");
                    l.add(EnumChatFormatting.DARK_PURPLE+"存储上限:"+((i!=15)?EnumChatFormatting.BLUE:EnumChatFormatting.DARK_RED)+
                            PrEXEMC.getEMCString(BigDecimal.valueOf(Math.pow(2,i-3)*100000).toBigInteger())+"emc");
                }

            }
        }
        super.addInformation(s, p, l, p_77624_4_);
    }
}
