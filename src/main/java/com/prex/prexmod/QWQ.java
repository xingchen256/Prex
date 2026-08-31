package com.prex.prexmod;

import com.prex.prexmod.emc.NetworkHandler;
import com.prex.prexmod.emc.PrEmcMap;
import com.prex.prexmod.emc.PrEmcMapS;
import com.prex.prexmod.emc.remcs;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class QWQ {
    private static int tick=0;
    private static Map<EntityPlayer,BigInteger> qwq=new HashMap<EntityPlayer,BigInteger>();
    public static String happy="ProjectReExtendedExchange";
    private static BigInteger rEmcs=BigInteger.ZERO;
    public static boolean hasRemcs(EntityPlayer player){
        return qwq.containsKey(player);
    }
    public static BigInteger getRemcs(EntityPlayer player){
        if(player==null)return BigInteger.ZERO;
        if(qwq.containsKey(player))return qwq.get(player);
        return BigInteger.ZERO;
    }
    public static void setRemcs(EntityPlayer a, BigInteger b){
        if(a!=null){
            if(b.compareTo(BigInteger.ZERO)==0){
                qwq.remove(a);
                return;
            }
            qwq.put(a,b);
        }
    }
    public static void addRemcs(EntityPlayer a, BigInteger b){
        setRemcs(a,getRemcs(a).add(b));
    }
    public static void sync(EntityPlayer player){
        NetworkHandler.CHANNEL.sendTo(
                new remcs(QWQ.getRemcs(player)),
                (EntityPlayerMP) player
        );
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event)
    {
        EntityPlayer player = event.player;
        setRemcs(player,BigInteger.ZERO);
        sync(player);
        NetworkHandler.REmcMap.sendTo(
                new PrEmcMapS(PrEmcMap.gets()),
                (EntityPlayerMP) player
        );
    }
}


