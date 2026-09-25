package com.prex.prexmod.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

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
        list.add(EnumChatFormatting.DARK_PURPLE+ StatCollector.translateToLocalFormatted("prex.tile.updateSpeedPowerFlower_tips"));
        list.add(EnumChatFormatting.DARK_PURPLE+StatCollector.translateToLocalFormatted("prex.tile.updateSpeed_size"));
        list.add(EnumChatFormatting.DARK_PURPLE+StatCollector.translateToLocalFormatted("prex.tile.updateSpeedPowerFlower_pt")+EnumChatFormatting.BLUE+(tww*60));
        list.add(EnumChatFormatting.GOLD+StatCollector.translateToLocalFormatted("prex.tile.updateSpeedPowerFlower_othertips"));
        super.addInformation(item, player, list, p_77624_4_);
    }
}
