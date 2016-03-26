package com.ermans.bottledanimals.block.generator;


import com.ermans.bottledanimals.block.BlockTile;
import com.ermans.bottledanimals.reference.Reference;

public abstract class BlockGenerator extends BlockTile {

    public BlockGenerator(String machineName) {
        super(machineName);
    }


    @Override
    protected String getTileSideTexture() {
        return Reference.MOD_ID_LOWERCASE + ":" + "generatorSide";
    }

    @Override
    protected String getTileTopTexture() {
        return Reference.MOD_ID_LOWERCASE + ":" + "generatorTop";
    }

    @Override
    protected String getTileBottomTexture() {
        return Reference.MOD_ID_LOWERCASE + ":" + "generatorDown";
    }

    @Override
    protected String getTileFrontTexture() {
        return Reference.MOD_ID_LOWERCASE + ":" + blockName;
    }



}
