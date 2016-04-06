package com.ermans.bottledanimals.network;

import com.ermans.bottledanimals.network.message.*;
import com.ermans.bottledanimals.reference.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID_LOWERCASE);

    public static void init() {

        INSTANCE.registerMessage(MessageTile.Handler.class, MessageTile.class, 1, Side.CLIENT);

        INSTANCE.registerMessage(MessageRedstoneButton.Handler.class, MessageRedstoneButton.class, 10, Side.SERVER);
        INSTANCE.registerMessage(MessageWirelessFeederButton.Handler.class, MessageWirelessFeederButton.class, 11, Side.SERVER);





    }
}
