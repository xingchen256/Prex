package com.prex.prexmod.emc;

import moze_intel.projecte.emc.SimpleStack;
import moze_intel.projecte.utils.EMCHelper;
import moze_intel.projecte.utils.ItemHelper;
import net.minecraft.item.ItemStack;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class PrEmcMap {
    private static Map<SimpleStack, BigInteger> emc=new HashMap<>();
    public static void setEmc(Map<SimpleStack, BigInteger> aemc){
        emc=aemc;
    }
    public static void clear(){
        emc=new HashMap<>();
    }
    public static Map<SimpleStack, BigInteger> gets(){return emc;}
    public static void put(SimpleStack simpleStack, BigInteger bigInteger){
        emc.put(simpleStack,bigInteger);
    }
    public static void put(ItemStack key,BigInteger bigInteger) {
        SimpleStack copy=new SimpleStack(key);
        put(copy,bigInteger);
    }
    public static void remove(ItemStack a) {
        emc.remove(new SimpleStack(a));
    }
    public static boolean contains(String key,int meta) {
        SimpleStack copy=new SimpleStack(ItemHelper.getStackFromString(key,meta));
        return contains(copy);
    }
    public static boolean contains(SimpleStack key) {
        SimpleStack copy = key.copy();
        copy.qnty = 1;
        return emc.containsKey(copy);
    }
    public static boolean contains(ItemStack key) {
        SimpleStack copy=new SimpleStack(key);
        return contains(copy);
    }
    public static BigInteger get(ItemStack stack) {
        SimpleStack copy = new SimpleStack(stack.copy());
        return get(copy);
    }
    public static BigInteger get(SimpleStack stack) {
        SimpleStack copy = stack.copy();
        copy.qnty = 1;
        ItemStack q=stack.toItemStack();
        if(PrExEmcMapFile.containsKey(q)){
            return new BigInteger(PrExEmcMapFile.getEMC(q));
        }
        return emc.get(copy);
    }
    public static final Comparator<ItemStack> EMCDDD = new Comparator<ItemStack>() {
        public int compare(ItemStack s1, ItemStack s2) {
            BigInteger emc1,emc2;
            if(!contains(s1)) {emc1=BigInteger.valueOf(EMCHelper.getEmcValue(s1)) ;}
            else{emc1 = get(s1);}
            if(!contains(s2)) {emc2=BigInteger.valueOf(EMCHelper.getEmcValue(s2)) ;}
            else{emc2 = get(s2);}
            int result = emc2.compareTo(emc1);

            return result;
        }
    };

}
