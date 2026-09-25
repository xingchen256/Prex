package com.prex.prexmod.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.List;

public class finalStar extends Item {
    @Override
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
        super.addInformation(p_77624_1_, p_77624_2_, list, p_77624_4_);
        if(p_77624_1_.getItem()==PrExItems.finalStar){
            list.add(EnumChatFormatting.DARK_PURPLE+ StatCollector.translateToLocalFormatted("prex.item.finalStart_tip1"));
            list.add(EnumChatFormatting.DARK_PURPLE+StatCollector.translateToLocalFormatted("prex.item.finalStart_tip2"));
            list.add(EnumChatFormatting.GOLD+StatCollector.translateToLocalFormatted("prex.item.finalStart_tip3"));
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_) {
        if (p_77613_1_.getItem()==PrExItems.finalStar) {
            return EnumRarity.epic;
        }else{
            return EnumRarity.rare;
        }
    }
}
