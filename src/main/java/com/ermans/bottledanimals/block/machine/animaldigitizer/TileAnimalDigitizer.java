package com.ermans.bottledanimals.block.machine.animaldigitizer;

import com.ermans.bottledanimals.block.machine.TileMachine;
import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.recipe.AnimalDigitizerManager;
import com.ermans.bottledanimals.recipe.IRecipe;
import net.minecraft.item.ItemStack;

public class TileAnimalDigitizer extends TileMachine {

    private static final AnimalDigitizerManager recManager = AnimalDigitizerManager.INSTANCE;
    private static final int bottle = 0;
    private static final int pattern = 1;
    private static final int output = 2;

    @Override
    public void initTile() {
        super.initTile();
        this.storage.setCapacity(DF_ENERGY_CAPACITY);
        this.storage.setMaxReceive(DF_ENERGY_MAX_RCV);
        this.storage.setMaxExtract(DF_ENERGY_MAX_EXT);
    }

    @Override
    protected int getNumInput() {
        return 2;
    }

    @Override
    protected int getNumOutput() {
        return 1;
    }

    @Override
    protected IRecipe getRecipeIfCanStart() {
        if (inventory[bottle] == null || inventory[pattern] == null) {
            return null;
        }
        AnimalDigitizerManager.AnimalDigitizerRecipe recipe = recManager.getRecipeIfValid(inventory[bottle], inventory[pattern]);

        if (recipe == null) {
            return null;
        }

        if (inventory[output] == null) {
            return recipe;
        }

        if (!inventory[output].isItemEqual(recipe.getOutput())) {
            return null;
        }
        if (recipe.getOutputStackSize() + inventory[output].stackSize <= inventory[output].getMaxStackSize()) {
            return recipe;
        }
        return null;
    }


    @Override
    protected boolean canStillProcess(int recipeCode) {
        if (inventory[bottle] == null || inventory[pattern] == null) {
            return false;
        }
        AnimalDigitizerManager.AnimalDigitizerRecipe recipe = recManager.getRecipeIfValid(inventory[bottle], inventory[pattern]);
        return recipe != null && recipe.getCode() == recipeCode;
    }

    @Override
    protected void finishProcess() {
        AnimalDigitizerManager.AnimalDigitizerRecipe recipe = recManager.getRecipeIfValid(inventory[bottle], inventory[pattern]);
        decrStackSize(bottle, recipe.getInput1StackSize());
        decrStackSize(pattern, recipe.getInput2StackSize());
        increaseStackSize(output, recipe.getOutput());
    }


    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        if (!this.invHelper.isInput(slot)) {
            return false;
        }
        if (slot == bottle) {
            return itemstack.getItem() == ModItems.itemAnimalInABottle;
        }
        if (slot == pattern) {
            return itemstack.getItem() == ModItems.itemBlankPattern;
        }
        return false;
    }

}
