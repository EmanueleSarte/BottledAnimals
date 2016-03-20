package com.ermans.bottledanimals.block.generator.basicgenerator;

import com.ermans.bottledanimals.block.generator.TileEnergyProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileBasicGenerator extends TileEnergyProvider {

    private static final int fuel = 0;

    protected int remaining;


    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (remaining > 0) {

                remaining--;


            } else {
                if (hasPassedRedstoneTest()) {
                    if (inventory[fuel] != null) {
                        int burnTime = TileEntityFurnace.getItemBurnTime(inventory[fuel]);
                        if (burnTime > 0) {
                            remaining = burnTime * 20;
                        }
                    }
                }
            }
        }
    }



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
        return false;
    }
}
