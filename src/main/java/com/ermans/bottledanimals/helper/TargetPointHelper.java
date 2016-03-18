package com.ermans.bottledanimals.helper;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.tileentity.TileEntity;

public class TargetPointHelper {

    private static NetworkRegistry.TargetPoint getTargetPoint(TileEntity entity, int range) {
        return new NetworkRegistry.TargetPoint(entity.getWorldObj().provider.dimensionId, entity.xCoord, entity.yCoord, entity.zCoord, range);
    }

    //should i use 128??
    public static NetworkRegistry.TargetPoint getTargetPoint(TileEntity entity) {
        return getTargetPoint(entity, 64);
    }
}
