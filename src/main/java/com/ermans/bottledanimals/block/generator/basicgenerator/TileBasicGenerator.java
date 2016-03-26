package com.ermans.bottledanimals.block.generator.basicgenerator;

import com.ermans.bottledanimals.block.generator.TileGenerator;
import net.minecraft.init.Items;
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
        return getFuelTime(itemStack) > 0;
    }


    @Override
    protected boolean canStart() {
        return inventory[fuel] != null && getFuelTime(inventory[fuel]) > 0;
    }

    @Override
    protected int startProcess() {
        int time = getFuelTime(inventory[fuel]);
        decrStackSize(fuel, 1);
        return time;
    }

    protected int getFuelTime(ItemStack itemStack) {
        if (itemStack.getItem() == Items.coal && itemStack.getItemDamage() == 1) {
            return getFuelTime(new ItemStack(Items.coal, 1, 0)) / 2;
        }
        return TileEntityFurnace.getItemBurnTime(itemStack) * 20;
    }

}
