package com.ermans.bottledanimals.block.simple;

import com.ermans.bottledanimals.block.BlockBase;
import com.ermans.bottledanimals.reference.Names;
import net.minecraft.block.Block;

public class BlockMachineFrame extends BlockBase {


    public BlockMachineFrame() {
        super(Names.Blocks.MACHINE_FRAME);
        setStepSound(Block.soundTypeStone);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
