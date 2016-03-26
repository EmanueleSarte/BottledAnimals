package com.ermans.bottledanimals.block.machine.animalmaterializer;

import com.ermans.bottledanimals.block.machine.TileMachine;
import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.recipe.AnimalMaterializerManager;
import com.ermans.bottledanimals.recipe.IRecipe;
import net.minecraft.item.ItemStack;


public class TileAnimalMaterializer extends TileMachine{

    private static final AnimalMaterializerManager recManager = AnimalMaterializerManager.INSTANCE;
    private static final int animal = 0;
    private static final int eggFrame = 1;
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
        if (inventory[animal] == null || inventory[eggFrame] == null) {
            return null;
        }
        AnimalMaterializerManager.AnimalMaterializerRecipe recipe = recManager.getRecipeIfValid(inventory[animal], inventory[eggFrame]);

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
        if (inventory[animal] == null || inventory[eggFrame] == null) {
            return false;
        }
        AnimalMaterializerManager.AnimalMaterializerRecipe recipe = recManager.getRecipeIfValid(inventory[animal], inventory[eggFrame]);
        return recipe != null && recipe.getCode() == recipeCode;
    }

    @Override
    protected void finishProcess() {
        AnimalMaterializerManager.AnimalMaterializerRecipe recipe = recManager.getRecipeIfValid(inventory[animal], inventory[eggFrame]);
        decrStackSize(animal, recipe.getInput1StackSize());
        decrStackSize(eggFrame, recipe.getInput2StackSize());
        increaseStackSize(output, recipe.getOutput());
    }


    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        if (!this.invHelper.isInput(slot)) {
            return false;
        }
        if (slot == animal) {
            return itemstack.getItem() == ModItems.itemDigitalizedAnimal;
        }
        if (slot == eggFrame) {
            return itemstack.getItem() == ModItems.itemSpawnEggFrame;
        }
        return false;
    }
}
