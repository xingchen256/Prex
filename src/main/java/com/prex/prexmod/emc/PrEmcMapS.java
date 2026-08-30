package com.prex.prexmod.emc;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import moze_intel.projecte.emc.SimpleStack;
import net.minecraft.item.ItemStack;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class PrEmcMapS implements IMessage {
    public Map<SimpleStack, BigInteger> emc;
    public PrEmcMapS() {
        emc=new HashMap<>();
    }
    public PrEmcMapS(Map<SimpleStack, BigInteger> emcs) {
        this.emc = emcs;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        emc = new HashMap<>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            ItemStack key = ByteBufUtils.readItemStack(buf);
            BigInteger value = new BigInteger(ByteBufUtils.readUTF8String(buf));
            SimpleStack stack = new SimpleStack(key);
            emc.put(stack, value);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(emc.size());
        for (Map.Entry<SimpleStack, BigInteger> entry : emc.entrySet()) {
            ByteBufUtils.writeItemStack(buf, entry.getKey().toItemStack());
            ByteBufUtils.writeUTF8String(buf,entry.getValue().toString());
        }
    }
}
