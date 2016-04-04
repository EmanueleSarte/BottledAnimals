package com.ermans.bottledanimals.helper;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TargetPointHelper {

    private static NetworkRegistry.TargetPoint getTargetPoint(World world, BlockPos pos, int range) {
        return new NetworkRegistry.TargetPoint(world.provider.getDimensionId(), pos.getX(), pos.getY(), pos.getZ(), range);
    }

    public static NetworkRegistry.TargetPoint getTargetPoint(World world, BlockPos pos) {
        return getTargetPoint(world, pos, 64);
    }
}
