package com.prex.prexmod.emc;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkHandler {

    public static final SimpleNetworkWrapper CHANNEL =
            NetworkRegistry.INSTANCE.newSimpleChannel("prex");
    public static final SimpleNetworkWrapper REmcMap=
            NetworkRegistry.INSTANCE.newSimpleChannel("RemcMap");

    public static void init() {
        CHANNEL.registerMessage(
                PacketSync.class,
                remcs.class,
                0,
                Side.CLIENT
        );
        REmcMap.registerMessage(
                PrEmcMapSync.class,
                PrEmcMapS.class,
                1,
                Side.CLIENT
        );
    }
}
