package com.prex.prexmod.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

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
        list.add(EnumChatFormatting.DARK_PURPLE+ StatCollector.translateToLocalFormatted("prex.tile.updateSpeed_tips"));
        list.add(EnumChatFormatting.DARK_PURPLE+StatCollector.translateToLocalFormatted("prex.tile.updateSpeed_size",qqw,Math.min(qqw,8),qqw));
        list.add(EnumChatFormatting.DARK_PURPLE+StatCollector.translateToLocalFormatted("prex.tile.updateSpeed_ptb")+EnumChatFormatting.BLUE+(tww*20));
        list.add(EnumChatFormatting.DARK_PURPLE+StatCollector.translateToLocalFormatted("prex.tile.updateSpeed_ptf")+EnumChatFormatting.BLUE+(tww*2));
        list.add(EnumChatFormatting.GOLD+StatCollector.translateToLocalFormatted("prex.tile.updateSpeed_othertips"));
        super.addInformation(item, player, list, p_77624_4_);
    }
}
