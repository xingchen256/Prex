package com.prex.prexmod.emc;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import moze_intel.projecte.PECore;
import moze_intel.projecte.emc.SimpleStack;
import moze_intel.projecte.utils.ItemHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class PrExEmcMapFile {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private static final File PrExFile=new File(PECore.CONFIG_DIR, "PrExEmcMap.cfg");
    private static final Type MAP_TYPE = new TypeToken<Map<String, String[]>>() {}.getType();
    public static Map<FuckItem ,String> PrExEmcMap;//格式:"ItemName":[meta,Emc](String)
    private static class FuckItem{//f**k item meta,f**k function
        public String Uname;
        public int meta;
        public FuckItem(String name, int meta){
            this.Uname=name;
            this.meta=meta;
        }
        public FuckItem(ItemStack itemStack){
            this.Uname=Item.itemRegistry.getNameForObject(itemStack.getItem());
            this.meta=itemStack.getItemDamage();
        }
        public SimpleStack toStack(){
            SimpleStack t=new SimpleStack(ItemHelper.getStackFromString(this.Uname,this.meta)).copy();
            t.qnty=1;
            return t;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof FuckItem){
                FuckItem a=(FuckItem) obj;
                return a.Uname.equals(this.Uname) && a.meta == this.meta;
            }
            return false;
        }
        @Override
        public int hashCode() {
            int result = Uname.hashCode();
            result = 31 * result + meta;
            return result;
        }
    }
    public static void addToFile(String name, int meta, BigInteger emc){
        PrExEmcMap.put(new FuckItem(name,meta),emc.toString());
        writeFile();
    }
    public static boolean containsKey(String name, int meta){
        return PrExEmcMap.containsKey(new FuckItem(name,meta));
    }
    public static void removeFromFile(String name,int meta){
        ItemStack pt=ItemHelper.getStackFromString(name,meta);
        PrEmcMap.remove(pt);//清除内存中的PrEmcMap
        PrExEmcMap.remove(new FuckItem(name,meta));//清理文件,与缓存
        writeFile();
    }
    public static boolean containsKey(ItemStack item){
        return containsKey(Item.itemRegistry.getNameForObject(item.getItem()), item.getItemDamage());
    }

    public static String getEMC(String name,int meta){
        return PrExEmcMap.get(new FuckItem(name,meta));
    }
    public static String getEMC(ItemStack item){
        return PrExEmcMap.get(new FuckItem(item));
    }
    public static void readFile() {//读取文件
        PrExEmcMap = new HashMap<>();
        if (!PrExFile.exists()) {
            writeFile();
        }
        try {
            String content = new String(Files.readAllBytes(PrExFile.toPath()));
            JsonArray array = new JsonParser().parse(content).getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                JsonObject obj = array.get(i).getAsJsonObject();
                String name = obj.get("name").getAsString();
                int meta = obj.get("meta").getAsInt();
                String emc = obj.get("Emc").getAsString();
                FuckItem item = new FuckItem(name,meta);
                PrExEmcMap.put(item, emc);
                PrEmcMap.put(item.toStack(),new BigInteger(emc));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeFile() {//写入文件
        JsonArray array = new JsonArray();
        for (Map.Entry<FuckItem, String> entry : PrExEmcMap.entrySet()) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", entry.getKey().Uname);
            obj.addProperty("meta", entry.getKey().meta);
            obj.addProperty("Emc", entry.getValue());
            array.add(obj);
        }
        try (FileWriter writer = new FileWriter(PrExFile)) {
            GSON.toJson(array, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
