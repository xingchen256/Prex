package com.prex.prexmod.emc;


import com.prex.prexmod.IPrexEMC;
import moze_intel.projecte.playerData.TransmutationProps;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class PrEXEMC {

    private static IPrexEMC getProps(EntityPlayer player)
    {
        return (IPrexEMC)
                TransmutationProps.getDataFor(player);
    }
    public static String getEMCString(BigInteger value) {//EMC的string获取
        if (GuiScreen.isShiftKeyDown())return NumberFormat.getInstance(Locale.US).format(value);
        List<String> Tww= Arrays.asList(" "," ","亿","万亿","兆","万兆","亿兆","万亿兆","京","万京","亿京","万亿京","兆京","万兆京","亿兆京","万亿兆京","垓","万垓","亿垓");
        for(int i=0;i<Tww.size();i++){
            if(value.compareTo(new BigInteger("10").pow((i+1)*4))<0){
                if(i==0||i==1)break;
                int Leg=value.toString().length();
                int st=((Leg%4==0)?4:Leg%4);
                String point="."+value.toString().substring(st,st+2);
                if(value.toString().charAt(st + 1) == '0'){
                    point="."+value.toString().charAt(st);
                    if(value.toString().charAt(st) == '0'){point="";}
                }
                return value.toString().substring(0,st)+point+Tww.get(i);
            }
        }
        return NumberFormat.getInstance(Locale.US).format(value);
    }



    public static BigInteger get(EntityPlayer player)
    {
        return getProps(player).prex$getEMC();
    }



    public static void set(
            EntityPlayer player,
            BigInteger value)
    {
        getProps(player)
                .prex$setEMC(value);
    }
    public static void set(
            EntityPlayer player,
            int value)
    {
        getProps(player)
                .prex$setEMC(BigInteger.valueOf(value));
    }



    public static void add(
            EntityPlayer player,
            BigInteger value)
    {
        set(
                player,
                get(player).add(value)
        );
    }
}