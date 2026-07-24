package com.prex.prexmod.item;

import com.prex.prexmod.block.PrExBlocks;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

import static com.prex.prexmod.ExampleMod.PREX_TAB;

public class Matter {
    public static final Item[] matter=new Item[12];
    public static void RegisterMatter(String MODID){
        for(int i=0;i<matter.length;i++){
            matter[i]=new Item()
                    .setUnlocalizedName("prex_matter_"+(i+1))
                    .setTextureName("prex:matter/"+PrExBlocks.Color[i+3])
                    .setCreativeTab(PREX_TAB);
            GameRegistry.registerItem(matter[i], "matter_"+(i+1), MODID);
        }
    }
}
