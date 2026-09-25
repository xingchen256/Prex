package com.prex.prexmod.emc;


import com.prex.prexmod.IPrexEMC;
import moze_intel.projecte.playerData.TransmutationProps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;


public class PrEXEMC {

    private static IPrexEMC getProps(EntityPlayer player)
    {
        return (IPrexEMC)
                TransmutationProps.getDataFor(player);
    }
    public static String getEMCString(BigInteger value) {//EMC的string获取
        if (GuiScreen.isShiftKeyDown())return NumberFormat.getInstance(Locale.US).format(value);
        int ta=3;//间隔
        String[] Tww;
        if(Minecraft.getMinecraft().gameSettings.language.equals("zh_CN")){
           Tww=new String[]{"亿","万亿","兆","万兆","亿兆","万亿兆","京","万京","亿京","万亿京",
                   "兆京","万兆京","亿兆京","万亿兆京", "垓","万垓","亿垓"};ta=4;
        }else
            Tww= new String[]{"M", "B", "T", "Qa", "Qi", "Sx", "Sp", "Oc", "No", "Dc", "Ud", "Dd", "Td",
                    "Qad", "Qid", "Sxd", "Spd", "Ocd", "Nod", "Vg", "Uv", "Dv", "Tv", "Qav"};
        int maxtw=Tww.length*ta+ta*3;//max=len(Tww)*4+12
        String _value=value.toString();
        int leg=_value.length();
        if (leg<ta*2+1) return NumberFormat.getInstance(Locale.US).format(value);
        int a=(leg-1)%ta+1;
        if(leg<maxtw+1) return  _value.substring(0,a)+((a==ta)?"":".")+_value.substring(a,ta)+Tww[(leg-1)/ta-2];
        else return _value.substring(0,leg-maxtw)+Tww[Tww.length-1];
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