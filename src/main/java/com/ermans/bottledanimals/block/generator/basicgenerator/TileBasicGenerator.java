package com.ermans.bottledanimals.block.generator.basicgenerator;

import com.ermans.bottledanimals.block.generator.TileGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileBasicGenerator extends TileGenerator {

    private static final int fuel = 0;


    @Override
    protected int getNumInput() {
        return 1;
    }

    @Override
    protected int getNumOutput() {
        return 0;
    }


    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return TileEntityFurnace.getItemBurnTime(itemStack) > 0;
    }


    @Override
    protected boolean canStart() {
        return inventory[fuel] != null && TileEntityFurnace.getItemBurnTime(inventory[fuel]) != 0;
    }

    @Override
    protected int startProcess() {
        return TileEntityFurnace.getItemBurnTime(inventory[fuel]) * 20;
    }

}
