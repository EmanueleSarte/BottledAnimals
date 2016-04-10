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
    private static final int output3 = 3;
    private static final int outputRare = 4;
    private static final int pattern = 5;

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
            if (inventory[pattern].stackSize + recipe.getBrokenPatternStackSize() > inventory[pattern].getMaxStackSize()) {
                return null;
            }
        }

        ItemStack[] stacksOutput = new ItemStack[]{recipe.getOutput1(true), recipe.getOutput2(true), recipe.getOutput3(true)};
        int[] sizeOutput = new int[]{recipe.getOutput1MaxSize(), recipe.getOutput2MaxSize(), recipe.getOutput3MaxSize()};

        if (inventory[output1] == null && inventory[output2] == null && inventory[output3] == null && inventory[outputRare] == null) {
            return recipe;
        }

        int i = 0;
        for (int index : new int[]{output1, output2, output3}) {
            if (inventory[index] != null && stacksOutput.length > i) {
                if (inventory[index].isItemEqual(stacksOutput[i])) {
                    if (sizeOutput[i] + inventory[index].stackSize > inventory[index].getMaxStackSize()) {
                        return null;
                    }
                } else {
                    return null;
                }
            }
            i++;
        }

        ItemStack rare = recipe.getOutputRare(true);
        if (rare == null || inventory[TileDropExtractor.outputRare] == null) {
            return recipe;
        }

        if (inventory[TileDropExtractor.outputRare].isItemEqual(rare)) {
            if (recipe.getOutputRareMaxSize() + inventory[outputRare].stackSize > inventory[outputRare].getMaxStackSize()) {
                return null;
            }
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
        increaseStackSize(output2, recipe.getOutput2());
        increaseStackSize(output3, recipe.getOutput3());
        increaseStackSize(outputRare, recipe.getOutputRare());
        increaseStackSize(pattern, recipe.getBrokenPattern());
    }

    @Override
    protected int getNumInput() {
        return 1;
    }

    @Override
    protected int getNumOutput() {
        return 5;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        if (!invHelper.isInput(slot)) {
            return false;
        }
        return slot == animal && itemstack.getItem() == ModItems.itemDigitalizedAnimal;
    }

}
