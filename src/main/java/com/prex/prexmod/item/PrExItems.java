package com.prex.prexmod.item;

import com.prex.prexmod.block.PrExBlocks;
import cpw.mods.fml.common.registry.GameRegistry;
import moze_intel.projecte.gameObjs.items.KleinStar;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;

import static com.prex.prexmod.ExampleMod.PREX_TAB;

public class PrExItems {
    public static final Item[] fule=new Item[11];
    public static Item mStars = new magnum_start();
    public static Item cStars=new colossal_star();
    public static Item gStars=new gargantuan_star();
    public static Item finalStar=new finalStar()
            .setUnlocalizedName("prex_final_star")
            .setTextureName("prex:star/final")
            .setCreativeTab(PREX_TAB)
            .setMaxStackSize(1);
    public static Item finalStarShard=new finalStar()
            .setUnlocalizedName("prex_final_star_shard")
            .setTextureName("prex:star/final_shard")
            .setCreativeTab(PREX_TAB);
    public static Item KnowledgeShareBook=new KnowledgeShareBook();
    public static void init(String MODID){
        Matter.RegisterMatter(MODID);
        GameRegistry.registerFuelHandler(new Fuel());
        registerFule(MODID);
        GameRegistry.registerItem(mStars, "magnum_start");
        GameRegistry.registerItem(cStars, "colossal_star");
        GameRegistry.registerItem(gStars, "gargantuan_star");
        GameRegistry.registerItem(finalStar, "final_star");
        GameRegistry.registerItem(finalStarShard, "final_star_shard");
        GameRegistry.registerItem(KnowledgeShareBook, "knowledgeShareBook");
    }
    public static void registerFule(String MODID){
            for(int i=0;i<fule.length;i++){
                fule[i]=new Item()
                        .setUnlocalizedName("prex_fuel_"+(i+1))
                        .setTextureName("prex:fuel/"+ PrExBlocks.Color[i+3])
                        .setCreativeTab(PREX_TAB);
                GameRegistry.registerItem(fule[i], "fuel_"+(i+1), MODID);
            }

    }
}
