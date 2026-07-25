package com.prex.prexmod;

import com.prex.prexmod.block.PrExBlocks;
import com.prex.prexmod.item.Matter;
import com.prex.prexmod.item.PrExItems;
import cpw.mods.fml.common.registry.GameRegistry;
import moze_intel.projecte.gameObjs.ObjHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static com.prex.prexmod.block.PrExBlocks.matters;


public class recipe {
    public static void init(){
        matter();
        fuel();
        block();
        relay();
        collector();
        emc_link();
        star();
        powerflower();
        misc();

    }
    public static void powerflower(){
        for(int i=0;i<PrExBlocks.power_flower.length;i++){
            GameRegistry.addRecipe(
                    new ItemStack(
                            Item.getItemFromBlock(PrExBlocks.power_flower[i]),1),
                    "QBQ",
                    "AAA",
                    "AAA",
                    'A', new ItemStack(Item.getItemFromBlock(PrExBlocks.basicRelays[i]),1),
                    'B', new ItemStack(Item.getItemFromBlock(PrExBlocks.EmcLink[i]),1),
                    'Q', new ItemStack(Item.getItemFromBlock(PrExBlocks.ccollector[i]),1)
            );
        }
    }
    public static void relay(){
        GameRegistry.addShapelessRecipe(
                new ItemStack(Item.getItemFromBlock(PrExBlocks.basicRelays[0]),1),
                new Object[]{
                        new ItemStack(Item.getItemFromBlock(ObjHandler.relay),1)
                });
        GameRegistry.addShapelessRecipe(
                new ItemStack(Item.getItemFromBlock(PrExBlocks.basicRelays[1]),1),
                new Object[]{
                        new ItemStack(Item.getItemFromBlock(ObjHandler.relayMK2),1)
                });
        GameRegistry.addShapelessRecipe(
                new ItemStack(Item.getItemFromBlock(PrExBlocks.basicRelays[2]),1),
                new Object[]{
                        new ItemStack(Item.getItemFromBlock(ObjHandler.relayMK3),1)
                });
        GameRegistry.addShapelessRecipe(
                new ItemStack(Item.getItemFromBlock(PrExBlocks.basicRelays[1]),1),
                new Object[]{
                        new ItemStack(Item.getItemFromBlock(PrExBlocks.basicRelays[0]),1),
                        new ItemStack(ObjHandler.matter,1)
                });
        GameRegistry.addShapelessRecipe(
                new ItemStack(Item.getItemFromBlock(PrExBlocks.basicRelays[2]),1),
                new Object[]{
                        new ItemStack(Item.getItemFromBlock(PrExBlocks.basicRelays[1]),1),
                        new ItemStack(ObjHandler.matter,1,1)
                });
        //不要用循环会崩溃
        for(int i=0;i<Matter.matter.length;i++){
            GameRegistry.addShapelessRecipe(
                    new ItemStack(Item.getItemFromBlock(PrExBlocks.basicRelays[i+3]),1),
                    new Object[]{
                            new ItemStack(Item.getItemFromBlock(PrExBlocks.basicRelays[i+2]),1),
                            new ItemStack(PrExBlocks.matters[i],1),
                    });
        }
        GameRegistry.addShapelessRecipe(
                new ItemStack(Item.getItemFromBlock(PrExBlocks.basicRelays[15]),1),
                new Object[]{
                        new ItemStack(Item.getItemFromBlock(PrExBlocks.basicRelays[14]),1),
                        new ItemStack(PrExItems.finalStarShard,1)
                });
    }

    public static void collector(){
        GameRegistry.addShapelessRecipe(
                new ItemStack(Item.getItemFromBlock(PrExBlocks.collector[0]),1),
                new Object[]{
                        new ItemStack(Item.getItemFromBlock(ObjHandler.energyCollector),1)
                });
        GameRegistry.addShapelessRecipe(
                new ItemStack(Item.getItemFromBlock(PrExBlocks.collector[1]),1),
                new Object[]{
                        new ItemStack(Item.getItemFromBlock(ObjHandler.collectorMK2),1)
                });
        GameRegistry.addShapelessRecipe(
                new ItemStack(Item.getItemFromBlock(PrExBlocks.collector[2]),1),
                new Object[]{
                        new ItemStack(Item.getItemFromBlock(ObjHandler.collectorMK3),1)
                });
        GameRegistry.addShapelessRecipe(
                new ItemStack(Item.getItemFromBlock(PrExBlocks.collector[1]),1),
                new Object[]{
                        new ItemStack(Item.getItemFromBlock(PrExBlocks.collector[0]),1),
                        new ItemStack(ObjHandler.matter,1)
                });
        GameRegistry.addShapelessRecipe(
                new ItemStack(Item.getItemFromBlock(PrExBlocks.collector[2]),1),
                new Object[]{
                        new ItemStack(Item.getItemFromBlock(PrExBlocks.collector[1]),1),
                        new ItemStack(ObjHandler.matter,1,1)
                });
        //不要用循环会崩溃
        for(int i=0;i<Matter.matter.length;i++){
            GameRegistry.addShapelessRecipe(
                    new ItemStack(Item.getItemFromBlock(PrExBlocks.collector[i+3]),1),
                    new Object[]{
                            new ItemStack(Item.getItemFromBlock(PrExBlocks.collector[i+2]),1),
                            new ItemStack(PrExBlocks.matters[i],1),
                    });
        }
        GameRegistry.addShapelessRecipe(
                new ItemStack(Item.getItemFromBlock(PrExBlocks.collector[15]),1),
                new Object[]{
                        new ItemStack(Item.getItemFromBlock(PrExBlocks.collector[14]),1),
                        new ItemStack(PrExItems.finalStarShard,1)
                });
    }
    public static void star(){
        ItemStack _i=new ItemStack(ObjHandler.kleinStars,1,5);
        GameRegistry.addShapelessRecipe(new ItemStack(PrExItems.mStars,1),
                new Object[]{_i,_i,_i,_i});
        for(int i = 1; i < 6; ++i) {
            ItemStack input=new ItemStack(PrExItems.mStars,1,i-1);
            GameRegistry.addShapelessRecipe(new ItemStack(PrExItems.mStars,1,i),
                    new Object[]{input,input,input,input});
            if(i>3){//莫名的bug补丁
                GameRegistry.addShapelessRecipe(new ItemStack(PrExItems.mStars,4,i-1),
                        new Object[]{new ItemStack(PrExItems.mStars,1,i)});
            }

        }
        ItemStack _a=new ItemStack(PrExItems.mStars,1,5);
        GameRegistry.addShapelessRecipe(new ItemStack(PrExItems.cStars,1),
                new Object[]{_a,_a,_a,_a});
        GameRegistry.addShapelessRecipe(new ItemStack(PrExItems.mStars,4,5),
                new Object[]{new ItemStack(PrExItems.cStars,1) });
        for(int i = 1; i < 6; ++i) {
            ItemStack input=new ItemStack(PrExItems.cStars,1,i-1);
            GameRegistry.addShapelessRecipe(new ItemStack(PrExItems.cStars,1,i),
                    new Object[]{input,input,input,input});
            GameRegistry.addShapelessRecipe(new ItemStack(PrExItems.cStars,4,i-1),
                    new Object[]{new ItemStack(PrExItems.cStars,1,i)});
        }
        ItemStack _q=new ItemStack(PrExItems.cStars,1,5);
        GameRegistry.addShapelessRecipe(new ItemStack(PrExItems.gStars,1),
                new Object[]{_q,_q,_q,_q,_q,_q,_q,_q,_q});
        GameRegistry.addShapelessRecipe(new ItemStack(PrExItems.cStars,9,5),
                new Object[]{new ItemStack(PrExItems.gStars,1) });
        for(int i = 1; i < 6; ++i) {
            ItemStack input=new ItemStack(PrExItems.gStars,1,i-1);
            GameRegistry.addShapelessRecipe(new ItemStack(PrExItems.gStars,1,i),
                    new Object[]{input,input,input,input,input,input,input,input,input});
            GameRegistry.addShapelessRecipe(new ItemStack(PrExItems.gStars,9,i-1),
                    new Object[]{new ItemStack(PrExItems.gStars,1,i)});
        }
//        GameRegistry.addRecipe(
//                new ItemStack(PrExItems.finalStarShard,1),
//                "AAA",
//                "ABA",
//                "AAA",
//                'A', new ItemStack(PrExItems.gStars,1,5),
//                'B', new ItemStack(Items.nether_star,1)
//        );
        GameRegistry.addRecipe(
                new ItemStack(PrExItems.finalStarShard,1),
                "AQA",
                "WBW",
                "AQA",
                'A', new ItemStack(PrExItems.gStars,1,5),
                'B', new ItemStack(Items.nether_star,1),
                'Q',new ItemStack(Item.getItemFromBlock(PrExBlocks.power_flower[14]),1),
                'W',new ItemStack(Item.getItemFromBlock(PrExBlocks.matters[11]),1)
        );
        GameRegistry.addRecipe(
                new ItemStack(PrExItems.finalStar,1),
                "QAQ",
                "ABA",
                "QAQ",
                'A', new ItemStack(PrExItems.finalStarShard,1),
                'B', new ItemStack(Item.getItemFromBlock(Blocks.dragon_egg) ,1),
                'Q',new ItemStack(Item.getItemFromBlock(PrExBlocks.power_flower[15]),1)
        );

    }
    public static void emc_link(){
        GameRegistry.addRecipe(
                new ItemStack(
                        Item.getItemFromBlock(PrExBlocks.EmcLink[0]),1),
                "CBA",
                "TQT",
                "ABC",
                'A', new ItemStack(ObjHandler.covalence,1,2),
                'B', new ItemStack(ObjHandler.covalence,1,1),
                'C', new ItemStack(ObjHandler.covalence,1),
                'T', new ItemStack(ObjHandler.transmutationTablet,1),
                'Q', new ItemStack(ObjHandler.condenser,1)
        );
        GameRegistry.addRecipe(
                new ItemStack(
                        Item.getItemFromBlock(PrExBlocks.EmcLink[1]),1),
                "CBA",
                "TQT",
                "ABC",
                'A', new ItemStack(ObjHandler.covalence,1,2),
                'B', new ItemStack(ObjHandler.covalence,1,1),
                'C', new ItemStack(ObjHandler.covalence,1),
                'T', new ItemStack(ObjHandler.matter,1),
                'Q', new ItemStack(Item.getItemFromBlock(PrExBlocks.EmcLink[0]),1)
        );

        GameRegistry.addRecipe(
                new ItemStack(
                        Item.getItemFromBlock(PrExBlocks.EmcLink[2]),1),
                "CBA",
                "TQT",
                "ABC",
                'A', new ItemStack(ObjHandler.covalence,1,2),
                'B', new ItemStack(ObjHandler.covalence,1,1),
                'C', new ItemStack(ObjHandler.covalence,1),
                'T', new ItemStack(ObjHandler.matter,1,1),
                'Q', new ItemStack(Item.getItemFromBlock(PrExBlocks.EmcLink[1]),1)
        );
        for (int i = 0; i< matters.length; i++){
            GameRegistry.addRecipe(
                    new ItemStack(
                            Item.getItemFromBlock(PrExBlocks.EmcLink[i+3]),1
                    ),
                    "CBA",
                    "TQT",
                    "ABC",
                    'A', new ItemStack(ObjHandler.covalence,1,2),
                    'B', new ItemStack(ObjHandler.covalence,1,1),
                    'C', new ItemStack(ObjHandler.covalence,1),
                    'T', new ItemStack(Item.getItemFromBlock(PrExBlocks.matters[i]),1),
                    'Q', new ItemStack(Item.getItemFromBlock(PrExBlocks.EmcLink[i+2]),1)
            );
        }
        GameRegistry.addRecipe(
                new ItemStack(
                        Item.getItemFromBlock(PrExBlocks.EmcLink[15]),1
                ),
                "CBA",
                "TQT",
                "ABC",
                'A', new ItemStack(ObjHandler.covalence,1,2),
                'B', new ItemStack(ObjHandler.covalence,1,1),
                'C', new ItemStack(ObjHandler.covalence,1),
                'T', new ItemStack(PrExItems.finalStarShard,1),
                'Q', new ItemStack(Item.getItemFromBlock(PrExBlocks.EmcLink[14]),1)
        );
    }
    public static void block(){
        for (int i = 0; i< matters.length; i++){//物质块注册
            GameRegistry.addRecipe(
                    new ItemStack(
                            Item.getItemFromBlock(matters[i]),1
                    ),
                    "HHH",
                    "HHH",
                    "HHH",
                    'H', new ItemStack(
                            Matter.matter[i],1
                    )
            );
        }
        for (int i = 0; i< matters.length; i++){//分解配方
            GameRegistry.addShapelessRecipe(
                    new ItemStack(Matter.matter[i],9),
                    new Object[]{
                            new ItemStack(Item.getItemFromBlock(PrExBlocks.matters[i]),1)
                    });
        }
        for (int i = 0; i< PrExBlocks.fuels.length; i++){//燃料块注册
            GameRegistry.addRecipe(
                    new ItemStack(
                            Item.getItemFromBlock(PrExBlocks.fuels[i]),1
                    ),
                    "HHH",
                    "HHH",
                    "HHH",
                    'H', new ItemStack(
                            PrExItems.fule[i],1
                    )
            );
        }
        for (int i = 0; i< PrExBlocks.fuels.length; i++){//分解配方
            GameRegistry.addShapelessRecipe(
                    new ItemStack(PrExItems.fule[i],9),
                    new Object[]{
                            new ItemStack(Item.getItemFromBlock(PrExBlocks.fuels[i]),1)
                    });
        }
        for(int i=0;i<PrExBlocks.ccollector.length; i++){
            GameRegistry.addRecipe(
                    new ItemStack(
                            Item.getItemFromBlock(PrExBlocks.ccollector[i]),1
                    ),
                    "HHH",
                    "HHH",
                    "HHH",
                    'H', new ItemStack(
                            Item.getItemFromBlock(PrExBlocks.collector[i]),1
                    )
            );
        }
        for(int i=0;i<PrExBlocks.ccollector.length; i++){
            GameRegistry.addShapelessRecipe(
                    new ItemStack(
                            Item.getItemFromBlock(PrExBlocks.collector[i]),9
                    ),new Object[]{
                            new ItemStack(Item.getItemFromBlock(PrExBlocks.ccollector[i]),1)
                    }
            );
        }
    }
    public static void fuel(){
        GameRegistry.addRecipe(
                new ItemStack(
                        PrExItems.fule[0],1
                ),
                "HHH",
                "Q  ",
                "   ",
                'H', new ItemStack(
                        ObjHandler.fuels, 1,2
                ),
                'Q',new ItemStack(ObjHandler.fuelBlock,1,2)
        );
        for(int i=1;i<PrExItems.fule.length;i++){
            GameRegistry.addRecipe(
                    new ItemStack(
                           PrExItems.fule[i],1
                    ),
                    "HHH",
                    "Q  ",
                    "   ",
                    'H', new ItemStack(
                            PrExItems.fule[i-1], 1
                    ),
                    'Q',new  ItemStack(PrExBlocks.fuels[i-1], 1)
            );
        }
    }
    public static void matter(){
        GameRegistry.addRecipe(
                new ItemStack(
                        Matter.matter[0],1
                ),
                "FQF",
                "HMH",
                "FQF",
                'H', new ItemStack(
                        ObjHandler.fuels, 1,2
                ),
                'F', new ItemStack(
                       Item.getItemFromBlock(ObjHandler.fuelBlock) , 1,2
                ),
                'M', new ItemStack(
                        Item.getItemFromBlock(ObjHandler.matterBlock) ,1,1
                ),
                'Q',new ItemStack(
                        ObjHandler.matter ,1,1
        )
        );
        GameRegistry.addRecipe(
                new ItemStack(
                        Matter.matter[0],1
                ),
                "FHF",
                "QMQ",
                "FHF",
                'H', new ItemStack(
                        ObjHandler.fuels, 1,2
                ),
                'F', new ItemStack(
                        Item.getItemFromBlock(ObjHandler.fuelBlock) , 1,2
                ),
                'M', new ItemStack(
                        Item.getItemFromBlock(ObjHandler.matterBlock) ,1,1
                ),
                'Q',new ItemStack(
                        ObjHandler.matter ,1,1
                )
        );//基础物质配方
        for(int i=1;i<Matter.matter.length;i++){//其它物质
            GameRegistry.addRecipe(
                    new ItemStack(
                            Matter.matter[i],1
                    ),
                    "FHF",
                    "QMQ",
                    "FHF",
                    'H', new ItemStack(
                            PrExItems.fule[i-1], 1
                    ),
                    'F',new ItemStack(
                            Item.getItemFromBlock(PrExBlocks.fuels[i-1]) ,1
                    ),
                    'M', new ItemStack(
                            Matter.matter[i-1],1
                    ),
                    'Q',new ItemStack(
                            Item.getItemFromBlock(PrExBlocks.matters[i-1]) , 1
                    )
            );
            GameRegistry.addRecipe(
                    new ItemStack(
                            Matter.matter[i],1
                    ),
                    "FQF",
                    "HMH",
                    "FQF",
                    'H', new ItemStack(
                            PrExItems.fule[i-1], 1
                    ),
                    'F',new ItemStack(
                            Item.getItemFromBlock(PrExBlocks.fuels[i-1]) ,1
                    ),
                    'M', new ItemStack(
                            Matter.matter[i-1],1
                    ),
                    'Q',new ItemStack(
                            Item.getItemFromBlock(PrExBlocks.matters[i-1]) , 1
                    )
            );
        }

    }
    public static void misc(){
        GameRegistry.addRecipe(
                new ItemStack(
                        PrExItems.KnowledgeShareBook,1
                ),
                "HQH",
                "QWQ",
                "HQH",
                'H', new ItemStack(
                        Item.getItemFromBlock(PrExBlocks.matters[3]), 1
                ),
                'W',new ItemStack(Items.writable_book ,1),
                'Q',new ItemStack(Items.nether_star , 1)
        );
        GameRegistry.addRecipe(
                new ItemStack(
                        ObjHandler.tome,1
                ),
                "HAH",
                "AQA",
                "HAH",
                'H', new ItemStack(PrExItems.KnowledgeShareBook, 1),
                'Q',new ItemStack(PrExItems.finalStar , 1),
                'A',new ItemStack(PrExItems.finalStarShard,1)
        );
    }
}
