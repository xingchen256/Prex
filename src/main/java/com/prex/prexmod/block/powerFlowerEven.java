package com.prex.prexmod.block;

import com.prex.prexmod.QWQ;
import com.prex.prexmod.emc.NetworkHandler;
import com.prex.prexmod.emc.PrEmcMap;
import com.prex.prexmod.emc.PrEmcMapS;
import com.prex.prexmod.emc.remcs;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import moze_intel.projecte.playerData.Transmutation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.math.BigInteger;

import static com.prex.prexmod.QWQ.setRemcs;
import static com.prex.prexmod.QWQ.sync;

public class powerFlowerEven {
    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if(event.phase != TickEvent.Phase.END)
            return;
        if(event.player.worldObj.isRemote)
            return;
        EntityPlayer player = event.player;
        if(player.ticksExisted%60==0){//3s一刷新
            if(QWQ.hasRemcs(player)){Transmutation.sync(player);}
        }
    }
    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event)
    {
        EntityPlayer player = event.player;
        setRemcs(player, BigInteger.ZERO);
        sync(player);
    }
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {//登入同步
        EntityPlayer player = event.player;

        if (player.getEntityWorld().isRemote) {
            return;
        }
        NetworkHandler.CHANNEL.sendTo(
                new remcs(QWQ.getRemcs(player)),
                (EntityPlayerMP) player
        );
        NetworkHandler.REmcMap.sendTo(
                new PrEmcMapS(PrEmcMap.gets()),
                (EntityPlayerMP) player
        );
    }
}
