package com.prex.prexmod;

//import com.prex.prexmod.emc.PrEMC;
//import com.prex.prexmod.emc.playerData;

import com.prex.prexmod.block.*;
import com.prex.prexmod.emc.NetworkHandler;
import com.prex.prexmod.emc.PrExEmcMapFile;
import com.prex.prexmod.item.PrExItems;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.ObjModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

/*
* 血的教训呀，千万不要把mod名改的比要依赖的mod先呀，
* 不然可能找了6个小时BUG才能找到原因，
* 不要自以为是的修改输出的jar名，没一点用的*/
@Mod(modid = ExampleMod.MODID, name = ExampleMod.NAME, useMetadata = true)
public class ExampleMod {
    public static final String MODID = "prex";
    public static final String NAME = "PrEX Mod";
    public static final String VERSION = "1.0.0";
    public static Configuration config;

    public static boolean enableRecipe;
    public static boolean enableTimerMachine;
    public static boolean enabledEE;

    public static final Logger LOG = LogManager.getLogger(NAME);

    @Mod.Instance(MODID)
    public static ExampleMod INSTANCE;
    public static CreativeTabs PREX_TAB = new CreatTab();

    public boolean obfuscated;

    @SideOnly(Side.CLIENT)
    public static void aa(){
        ClientRegistry.bindTileEntitySpecialRenderer(
                TilePowerFlower.class,
                new PowerFlowerRender()
        );
        for(PowerFlower a:PrExBlocks.power_flower){
            MinecraftForgeClient.registerItemRenderer(
                    Item.getItemFromBlock(a),
                    new PowerFlowerItemRender()
            );
        }
    }
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (Loader.isModLoaded("Waila"))
            FMLInterModComms.sendMessage("Waila", "register", "com.prex.prexmod.wailaHandler.callbackRegister");
        PrExEmcMapFile.readFile();
        INSTANCE = this;
        config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        enableRecipe = config.get(
                Configuration.CATEGORY_GENERAL,
                "enableRecipe",
                true,
                "启用新的配方(更高EMC的合成)"
        ).getBoolean();
        if (!enableRecipe) {
            for (int i=0;i<PowerFlower.gen.length;i++){
                if (i<11){
                    PowerFlower.gen[i]/=10;
                }else if (i!=16){
                    PowerFlower.gen[i]/=200;
                }else{PowerFlower.gen[16]=2000000000000000L;}
            }
        }
        enableTimerMachine = config.get(
                Configuration.CATEGORY_GENERAL,
                "enableTimerMachine",
                true,
                "启用时间立场机器"
        ).getBoolean();

        if (config.hasChanged()) {
            config.save();
        }
        MinecraftForge.EVENT_BUS.register(this);
        GameRegistry.registerTileEntity(
                TilePowerFlower.class,
                "prex_power_flower"
        );
        GameRegistry.registerTileEntity(
                TileSpeedUpdateBlock.class,
                "prex_speed_update_block"
        );
        GameRegistry.registerTileEntity(
                TileCollector.class,
                "prex_collector"
        );
        GameRegistry.registerTileEntity(
                TileRelay.class,
                "prex_relay"
        );
        GameRegistry.registerTileEntity(
                TileSpeedUpdatePowerFlower.class,
                "prex_speed_update_power_flower"
        );
        AdvancedModelLoader.registerModelHandler(
                new ObjModelLoader()
        );
        NetworkHandler.init();
        FMLCommonHandler.instance().bus().register(new powerFlowerEven());

//        PrEmcMap.put(new SimpleStack(new ItemStack(Item.getItemFromBlock(PrExBlocks.matters[11]))),new BigInteger("41000047483647"));
    }

    @Mod.EventHandler
    public void commonInit(FMLInitializationEvent event) {
        PrExBlocks.init();
        PrExItems.init(MODID);
        aa();
        if (enableRecipe) {
            recipe.init();
        }else{
            oldRecipe.init();
        }

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        this.obfuscated = !(Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
        try {
            Class<?> clazz = Class.forName(
                    "moze_intel.projecte.gameObjs.items.TimeWatch"
            );

            Method method = clazz.getMethod(
                    "blacklist",
                    Class.class
            );
            method.invoke(null, TileSpeedUpdateBlock.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Mod.EventHandler
    public void onServerStartingEvent(FMLServerStartingEvent event) {
    }
}
