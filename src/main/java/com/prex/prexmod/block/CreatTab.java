package com.prex.prexmod.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreatTab extends CreativeTabs {


    public CreatTab()
    {
        super("prex");
    }


    @Override
    public Item getTabIconItem()
    {
        return Item.getItemFromBlock(
                PrExBlocks.basicRelays[0]
        );
    }
}