package com.prex.prexmod.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class finalStar extends Item {
    @Override
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
        super.addInformation(p_77624_1_, p_77624_2_, list, p_77624_4_);
        if(p_77624_1_.getItem()==PrExItems.finalStar){
            list.add(EnumChatFormatting.DARK_PURPLE+"把它放到转化桌的左边试试,会给你提供200兆EMC/次");
            list.add(EnumChatFormatting.DARK_PURPLE+"EMC太高了?把它放到右边的中间试试,他会吸收你所有的EMC");
            list.add(EnumChatFormatting.GOLD+"哦对了,这上面说反了");
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
