package com.prex.prexmod.block;

import com.prex.prexmod.emc.PrEXEMC;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.math.BigInteger;
import java.util.List;

public class PowerFlowerItem extends ItemBlock {
    public PowerFlowerItem(Block p_i45328_1_) {
        super(p_i45328_1_);
    }
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        String emc="0/s";
        for(int i=1;i<17;i++){
            if(Item.getItemFromBlock(PrExBlocks.power_flower[i-1])==stack.getItem()){
                if(i==16){
                    emc=EnumChatFormatting.DARK_RED+PrEXEMC.getEMCString(BigInteger.valueOf(PowerFlower.gen[i]));
                }
                else if(i>=12){
                    emc=EnumChatFormatting.RED+PrEXEMC.getEMCString(BigInteger.valueOf(PowerFlower.gen[i])) ;
                }
                else if(i>=7){
                    emc=EnumChatFormatting.GOLD+PrEXEMC.getEMCString(BigInteger.valueOf(PowerFlower.gen[i]));
                }else{
                    emc=EnumChatFormatting.GREEN+PrEXEMC.getEMCString(BigInteger.valueOf(PowerFlower.gen[i])) ;
                }

                break;
            }
        }
        list.add(EnumChatFormatting.YELLOW + "最高效率: "+emc+"emc/s");
    }
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.rare;
    }
}
