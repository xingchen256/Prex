package com.prex.prexmod.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class SpeedUpdatePowerFlowerItem extends ItemBlock {
    private int tww;
    public SpeedUpdatePowerFlowerItem(Block p_i45328_1_) {
        super(p_i45328_1_);
        this.tww= ((SpeedUpdatePowerFlower)p_i45328_1_).tww;
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean p_77624_4_) {
        int qqw=Math.min(tww*4,8)*2;
        list.add(EnumChatFormatting.DARK_PURPLE+"用于加速周围力量花盆的工作速度");
        list.add(EnumChatFormatting.DARK_PURPLE+String.format("作用范围:"+EnumChatFormatting.GREEN+" %sx%sx%s",qqw,Math.min(qqw,8),qqw)+"(以自身为中心)");
        list.add(EnumChatFormatting.DARK_PURPLE+"加速倍率: "+EnumChatFormatting.BLUE+(tww*60));
        list.add(EnumChatFormatting.GOLD+"对腐竹友好");
        super.addInformation(item, player, list, p_77624_4_);
    }
}
