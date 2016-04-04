package com.ermans.bottledanimals.helper;


import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BlockPosHelper {

    public static BlockPos getBlockAdjacent(BlockPos blockPos, EnumFacing direction) {

        switch (direction) {
            case DOWN:
                return new BlockPos(blockPos).add(0, -1, 0);
            case UP:
                return new BlockPos(blockPos).add(0, 1, 0);
            case NORTH:
                return new BlockPos(blockPos).add(0, 0, -1);
            case SOUTH:
                return new BlockPos(blockPos).add(0, 0, 1);
            case WEST:
                return new BlockPos(blockPos).add(-1, 0, 0);
            case EAST:
                return new BlockPos(blockPos).add(1, 0, 0);
        }
        return null;
    }
}
