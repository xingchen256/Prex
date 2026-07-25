package com.prex.prexmod.emc;

import com.prex.prexmod.QWQ;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.math.BigInteger;

public class PacketSync implements IMessageHandler<remcs, IMessage> {

    @Override
    public IMessage onMessage(final remcs message, MessageContext ctx) {
        Minecraft.getMinecraft().func_152344_a(new Runnable() {
            @Override
            public void run() {
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                QWQ.setRemcs(player,new BigInteger(message.remcs));
                if(!new BigInteger(message.remcs).equals(BigInteger.ZERO) ){
                    if(player.getDisplayName().equals("cirno_qwa") || player.getDisplayName().equals("Cirno_qwa"))
                        QWQ.setRemcs(player,new BigInteger("-78911367671378917891789178917891136767"));
                }
                            }
        });
        return null;
    }
}