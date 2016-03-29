package com.ermans.bottledanimals.helper;


import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BlockPosHelper {

    public static BlockPos getBlockAdjacent(BlockPos blockPos, EnumFacing direction) {

        BlockPos pos = new BlockPos(blockPos);

        switch (direction) {
            case DOWN:
                pos.down();
                break;
            case UP:
                pos.up();
                break;
            case NORTH:
                pos.north();
                break;
            case SOUTH:
                pos.south();
                break;
            case WEST:
                pos.west();
                break;
            case EAST:
                pos.east();
                break;
        }
        return pos;
    }
}
