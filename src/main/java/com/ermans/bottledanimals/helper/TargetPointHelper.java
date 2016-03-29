package com.ermans.bottledanimals.helper;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TargetPointHelper {

    private static NetworkRegistry.TargetPoint getTargetPoint(TileEntity entity, int range) {
        return new NetworkRegistry.TargetPoint(entity.getWorld().provider.getDimensionId(), entity.getPos().getX(), entity.getPos().getY(),entity.getPos().getZ(), range);
    }

    //should i use 128??
    public static NetworkRegistry.TargetPoint getTargetPoint(TileEntity entity) {
        return getTargetPoint(entity, 64);
    }
}
