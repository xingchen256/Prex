package com.prex.prexmod.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class SpeedUpdateItem extends ItemBlock {
    private int tww=1;
    public SpeedUpdateItem(Block block) {
        super(block);
        this.tww=((SpeedUpdateBlock)block).tww;

    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean p_77624_4_) {
        int qqw=Math.min(tww*4,8)*2;
        list.add(EnumChatFormatting.DARK_PURPLE+String.format("作用范围:"+EnumChatFormatting.GREEN+" %sx%sx%s",qqw,Math.min(qqw,8),qqw)+"(以自身为中心)");
        list.add(EnumChatFormatting.DARK_PURPLE+"最高作用系数: "+EnumChatFormatting.BLUE+(tww*20));
        super.addInformation(item, player, list, p_77624_4_);
    }
}
