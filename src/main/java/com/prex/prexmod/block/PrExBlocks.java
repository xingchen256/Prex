package com.prex.prexmod.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import static com.prex.prexmod.ExampleMod.PREX_TAB;

public class PrExBlocks {
    public static String[] Color={"basic","dark","red","magenta","pink","purple","violet","blue","cyan","green","lime","yellow","orange","white","fading","final"};
    public static Block[] basicRelays = new Block[16];
    public static Block[] fuels = new Block[11];
    public static Block[] matters=new Block[12];
    public static Block[] collector=new Block[16];
    public static Block[] ccollector=new Block[16];
    public static PowerFlower[] power_flower=new PowerFlower[16];
    public static Block[] speedupdate=new Block[6];//红,粉红,紫罗兰,绿,橙色,终极
    public static Block[] EmcLink=new Block[16];
    public static void init()
    {
        for(int i = 0; i < 16; i++)
        {
            basicRelays[i] = new Relay(i+1);
//                    .setBlockName("basic_relay_mk" + (i + 1))
//                    .setBlockTextureName("prex:relay/" + Color[i])
//                    .setCreativeTab(PREX_TAB);
            GameRegistry.registerBlock(
                    basicRelays[i],
                    RelayItem.class,
                    "prex_relay_mk" + (i + 1)
            );
        }
        for(int i=0;i<6;i++){
            speedupdate[i]=new SpeedUpdateBlock(i+1);
            GameRegistry.registerBlock(
                    speedupdate[i],SpeedUpdateItem.class,"prex_speed_update_block_mk" + (i+1));
        }
        for(int i = 0; i < 16; i++)
        {
            collector[i] = new Collector(i+1);
            GameRegistry.registerBlock(
                    collector[i],
                    Collector_Item.class,
                    "collector_mk" + (i + 1)

            );
        }
        for(int i = 0; i < 16; i++)
        {
            EmcLink[i] = new basicBlock(Material.rock,i+1)
                    .setBlockName("emc_link_mk" + (i + 1))
                    .setBlockTextureName("prex:emc_link/" + Color[i])
                    .setCreativeTab(PREX_TAB);
            GameRegistry.registerBlock(
                    EmcLink[i],
                    "emc_link_mk" + (i + 1)
            );
        }
        for(int i = 0; i < 16; i++)
        {
            ccollector[i] = new basicBlock(Material.glass,i+1)
                    .setBlockName("ccollector_mk" + (i + 1))
                    .setBlockTextureName("prex:ccollector/" + Color[i])
                    .setCreativeTab(PREX_TAB)
                    .setLightLevel(8+i);
            GameRegistry.registerBlock(
                    ccollector[i],
                    "ccollector_mk" + (i + 1)
            );
        }
        for(int i=0;i<16;i++){
            PowerFlower b =new PowerFlower(i+1);
            GameRegistry.registerBlock(
                    b,PowerFlowerItem.class,
                    "power_flower_"+(i+1)
            );
            power_flower[i]=b;
        }

        for(int i = 0; i < 11; i++)
        {
            fuels[i] = new Fule_Block(Material.iron)
                    .setBlockName("prex_fuel_block_" + (i + 1))
                    .setBlockTextureName("prex:fuel/" + Color[i+3])
                    .setCreativeTab(PREX_TAB);
            GameRegistry.registerBlock(
                    fuels[i],
                    "prex_fuel_block_" + (i + 1)
            );
        }
        for(int i = 0; i < 12; i++)
        {
            matters[i] = new Fule_Block(Material.iron)
                    .setBlockName("prex_matter_block_" + (i + 1))
                    .setBlockTextureName("prex:matter/" + Color[i+3])
                    .setCreativeTab(PREX_TAB);
            GameRegistry.registerBlock(
                    matters[i],
                    "matter_block_" + (i + 1)
            );
        }
    }
}
