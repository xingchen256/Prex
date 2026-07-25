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

public class RelayItem extends ItemBlock {

    public RelayItem(Block p_i45328_1_) {
        super(p_i45328_1_);
    }

    @Override
    public void addInformation(ItemStack s, EntityPlayer p, List l, boolean p_77624_4_) {
        for(int i=0;i<PrExBlocks.basicRelays.length;i++){
            if(Item.getItemFromBlock(PrExBlocks.basicRelays[i])==s.getItem()){
                if(i<3){

                    switch (i){
                        case 0:{
                            l.add(EnumChatFormatting.DARK_PURPLE+"最大输出:"+EnumChatFormatting.BLUE+64);
                            l.add(EnumChatFormatting.DARK_PURPLE+"存储上限:"+EnumChatFormatting.BLUE+10000);
                            break;
                        }
                        case 1:{
                            l.add(EnumChatFormatting.DARK_PURPLE+"最大输出:"+EnumChatFormatting.BLUE+192);
                            l.add(EnumChatFormatting.DARK_PURPLE+"存储上限:"+EnumChatFormatting.BLUE+100000);
                            break;
                        }
                        case 2:{
                            l.add(EnumChatFormatting.DARK_PURPLE+"最大输出:"+EnumChatFormatting.BLUE+640);
                            l.add(EnumChatFormatting.DARK_PURPLE+"存储上限:"+EnumChatFormatting.BLUE+1000000);
                            break;}
                    }

                }else{
                    l.add(EnumChatFormatting.DARK_PURPLE+"最高输出:"+EnumChatFormatting.BLUE+
                            PrEXEMC.getEMCString(BigInteger.valueOf(64*(long)Math.pow(i+1,2))));
                    l.add(EnumChatFormatting.DARK_PURPLE+"存储上限:"+((i!=15)?EnumChatFormatting.BLUE:EnumChatFormatting.DARK_RED)+
                            PrEXEMC.getEMCString(BigDecimal.valueOf(Math.pow(i-2,2)*1000_0000).toBigInteger()));
                }

            }
        }
        super.addInformation(s, p, l, p_77624_4_);
    }
}
