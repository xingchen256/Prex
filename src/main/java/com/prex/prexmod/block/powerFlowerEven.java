package com.prex.prexmod.block;

import com.prex.prexmod.QWQ;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import moze_intel.projecte.playerData.Transmutation;
import net.minecraft.entity.player.EntityPlayer;

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
}
