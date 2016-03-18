package com.ermans.bottledanimals.block.machine.dropextractor;

import com.ermans.bottledanimals.block.machine.TileMachine;
import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.recipe.DropExtractorManager;
import com.ermans.bottledanimals.recipe.IRecipe;
import net.minecraft.item.ItemStack;

public class TileDropExtractor extends TileMachine {
    private static final DropExtractorManager recManager = DropExtractorManager.INSTANCE;

    private static final int animal = 0;
    private static final int output1 = 1;
    private static final int output2 = 2;
    private static final int pattern = 3;

    @Override
    public void initTile() {
        super.initTile();
        this.storage.setCapacity(DF_ENERGY_CAPACITY);
        this.storage.setMaxReceive(DF_ENERGY_MAX_RCV);
        this.storage.setMaxExtract(DF_ENERGY_MAX_EXT);

    }

    @Override
    protected IRecipe getRecipeIfCanStart() {
        if (inventory[animal] == null) {
            return null;
        }
        DropExtractorManager.DropExtractorRecipe recipe = recManager.getRecipeIfValid(inventory[animal]);
        if (recipe == null) {
            return null;
        }

        if (inventory[pattern] != null) {
            if (inventory[pattern].stackSize + recipe.getOutput3StackSize() > inventory[pattern].getMaxStackSize()) {
                return null;
            }
        }

        ItemStack[] stacksOutput = new ItemStack[]{recipe.getOutput1(), recipe.getOutput2()};

        if (inventory[output1] == null && inventory[output2] == null) {
            return recipe;
        }

        int i = 0;
        for (int index : new int[]{output1, output2}) {
            if (inventory[index] != null && stacksOutput.length > i) {
                if (inventory[index].isItemEqual(stacksOutput[i])) {
                    if (stacksOutput[i].stackSize + inventory[index].stackSize > inventory[index].getMaxStackSize()) {
                        return null;
                    }
                } else {
                    return null;
                }
            }
            i++;
        }

        return recipe;
    }

    @Override
    protected void startProcess() {
    }

    @Override
    protected boolean canStillProcess(int recipeCode) {
        if (inventory[animal] == null) {
            return false;
        }
        DropExtractorManager.DropExtractorRecipe recipe = recManager.getRecipeIfValid(inventory[animal]);
        return recipe != null && recipe.getCode() == recipeCode;
    }

    @Override
    protected void finishProcess() {
        DropExtractorManager.DropExtractorRecipe recipe = recManager.getRecipeIfValid(inventory[animal]);
        decrStackSize(animal, recipe.getInputStackSize());
        increaseStackSize(output1, recipe.getOutput1());
        if (recipe.getOutput2() != null) {
            increaseStackSize(output2, recipe.getOutput2());
        }
        increaseStackSize(pattern, recipe.getOutput3());
    }

    @Override
    protected int getNumInput() {
        return 1;
    }

    @Override
    protected int getNumOutput() {
        return 3;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        if (!invHelper.isInput(slot)) {
            return false;
        }
        return slot == animal && itemstack.getItem() == ModItems.itemDigitalizedAnimal;
    }

}
