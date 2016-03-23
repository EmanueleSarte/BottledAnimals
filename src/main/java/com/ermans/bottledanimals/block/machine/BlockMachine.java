package com.ermans.bottledanimals.block.machine;


import com.ermans.bottledanimals.block.BlockTile;
import com.ermans.bottledanimals.reference.Reference;

public abstract class BlockMachine extends BlockTile {


    public BlockMachine(String machineName) {
        super(machineName);
    }

    @Override
    protected String getTileSideTexture() {
        return Reference.MOD_ID_LOWERCASE + ":" + "machineSide";
    }

    @Override
    protected String getTileTopTexture() {
        return Reference.MOD_ID_LOWERCASE + ":" + "machineTop";
    }

    @Override
    protected String getTileBottomTexture() {
        return Reference.MOD_ID_LOWERCASE + ":" + "machineDown";
    }

    @Override
    protected String getTileFrontTexture() {
        return Reference.MOD_ID_LOWERCASE + ":" + blockName;
    }
}
