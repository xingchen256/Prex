package com.prex.prexmod.item;

import com.prex.prexmod.block.PrExBlocks;
import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Fuel implements IFuelHandler {
    @Override
    public int getBurnTime(ItemStack itemStack) {
        if(itemStack==null){return 0;}
        for(int i=0;i<PrExItems.fule.length;i++){
            if(itemStack.getItem()==PrExItems.fule[i]){
                return (i+1)*2048;
            }
            if(itemStack.getItem()== Item.getItemFromBlock(PrExBlocks.fuels[i])){
                return (i+1)*2048*9;
            }
        }
        return 0;
    }
}
