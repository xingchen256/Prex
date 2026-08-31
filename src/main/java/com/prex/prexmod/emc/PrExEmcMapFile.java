package com.prex.prexmod.emc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import moze_intel.projecte.PECore;
import moze_intel.projecte.emc.SimpleStack;
import moze_intel.projecte.utils.FileHelper;
import moze_intel.projecte.utils.ItemHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.*;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class PrExEmcMapFile {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private static final File PrExFile=new File(PECore.CONFIG_DIR, "PrExEmcMap.cfg");
    private static final Type MAP_TYPE = new TypeToken<Map<String, String[]>>() {}.getType();
    public static Map<SimpleStack ,String> PrExEmcMap;//格式:"ItemName":[meta,Emc](String)
    private class FuckItem{
        public String Uname;
        public int meta;
        public FuckItem(String name, int meta){
            this.Uname=name;
            this.meta=meta;
        }
    }
    public static boolean addToFile(String name, int meta, BigInteger emc){
        PrExEmcMap.put(new SimpleStack(ItemHelper.getStackFromString(name,meta)),emc.toString());
        return writeFile();
    }
    public static boolean containsKey(String name, int meta){
        return PrExEmcMap.containsKey(new SimpleStack(ItemHelper.getStackFromString(name,meta)));
    }
    public static void removeFromFile(String name,int meta){
        ItemStack pt=ItemHelper.getStackFromString(name,meta);
        PrEmcMap.remove(pt);//清除内存中的PrEmcMap
        PrExEmcMap.remove(new SimpleStack(pt));//清理文件,与缓存
        writeFile();
    }
    public static boolean containsKey(ItemStack item){
        return containsKey(Item.itemRegistry.getNameForObject(item.getItem()), item.getItemDamage());
    }

    public static String getEMC(String name,int meta){
        return PrExEmcMap.get(new SimpleStack(ItemHelper.getStackFromString(name,meta)));
    }
    public static String getEMC(ItemStack item){
        return PrExEmcMap.get(new SimpleStack(item));
    }
    public static boolean readFile() {//读取文件
        if (!PrExFile.exists() || !PrExFile.isFile()) {
            PrExEmcMap = new HashMap<>();
            return false;
        }
        boolean flag = true;
        Reader reader = null;
        try {
            PrExEmcMap.clear();
            reader = new InputStreamReader(Files.newInputStream(PrExFile.toPath()), StandardCharsets.UTF_8);
            Map<String, String[]> map = GSON.fromJson(reader, MAP_TYPE);
            for (String i:map.keySet()) {
                ItemStack pt=ItemHelper.getStackFromString(i,Integer.parseInt(map.get(i)[0]));
                PrExEmcMap.put(new SimpleStack(pt),map.get(i)[1]);
                //写入到PrEmcMap中原版等价会挂名一个int_max的值
                PrEmcMap.put(pt,new BigInteger(map.get(i)[1]));
                if(map.get(i).equals("0"))PrEmcMap.remove(pt);

            }
        } catch (Exception e) {
            PrExEmcMap=new HashMap<>();
            flag=false;
        }finally {
            if(reader!=null){
                FileHelper.closeStream(reader);
            }else{
                flag=false;
            }
        }
        return flag;
    }
    public static boolean writeFile() {//写入文件
        if (PrExEmcMap == null) return false;
        boolean flag=true;
        Writer writer = null;
        try  {
            writer= new OutputStreamWriter(Files.newOutputStream(PrExFile.toPath()), StandardCharsets.UTF_8);
            Map<String,String[]> qtw=new HashMap<>();
            for (ItemStack i:PrExEmcMap.keySet()) {
                qtw.put(Item.itemRegistry.getNameForObject(i.getItem()),new String[]{String.valueOf(i.getItemDamage()),PrExEmcMap.get(i)});
            }
            GSON.toJson(qtw, writer);
        }catch (Exception e) {
            flag = false;
        }
        finally {
            if (writer!=null){
            FileHelper.closeStream(writer);}
            else{flag=false;}
        }
        return flag;
    }
}
