package com.prex.prexmod.emc;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

import java.math.BigInteger;

public class remcs implements IMessage {

    public String remcs;
    public remcs() {
    }
    public remcs(BigInteger remcs) {
        this.remcs = remcs.toString();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        remcs = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, remcs);
    }
}
