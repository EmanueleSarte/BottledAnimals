package com.ermans.bottledanimals.helper;


import net.minecraftforge.common.util.ForgeDirection;

public class BlockPosHelper {


    public static BlockPos getBlockAdjacent(BlockPos blockPos, ForgeDirection direction) {

        BlockPos pos = new BlockPos(blockPos);

        switch (direction) {
            case DOWN:
                pos.y -= 1;
                break;
            case UP:
                pos.y += 1;
                break;
            case NORTH:
                pos.z -= 1;
                break;
            case SOUTH:
                pos.z += 1;
                break;
            case WEST:
                pos.x -= 1;
                break;
            case EAST:
                pos.x += 1;
                break;
            default:
                pos = null;
        }
        return pos;
    }

    public static BlockPos getBlockAdjacent(int x, int y, int z, ForgeDirection direction) {

        BlockPos pos = new BlockPos(x, y, z);

        switch (direction) {
            case DOWN:
                pos.y -= 1;
                break;
            case UP:
                pos.y += 1;
                break;
            case NORTH:
                pos.z -= 1;
                break;
            case SOUTH:
                pos.z += 1;
                break;
            case WEST:
                pos.x -= 1;
                break;
            case EAST:
                pos.x += 1;
                break;
            default:
                pos = null;
        }
        return pos;
    }
}
