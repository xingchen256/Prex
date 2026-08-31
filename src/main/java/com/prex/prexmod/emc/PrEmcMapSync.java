package com.prex.prexmod.emc;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;

public class PrEmcMapSync implements IMessageHandler<PrEmcMapS, IMessage> {
    @Override
    public IMessage onMessage(final PrEmcMapS message, MessageContext messageContext) {
        Minecraft.getMinecraft().func_152344_a(new Runnable() {
            @Override
            public void run() {
                PrEmcMap.clear();
                PrEmcMap.setEmc(message.emc);
            }
        });
        return null;
    }
}
