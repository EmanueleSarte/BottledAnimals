package com.ermans.bottledanimals.block.machine.breeder;

import com.ermans.bottledanimals.block.machine.TileMachine;
import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.recipe.BreederManager;
import com.ermans.bottledanimals.recipe.IRecipe;
import com.ermans.bottledanimals.reference.Animals;
import net.minecraft.item.ItemStack;

public class TileBreeder extends TileMachine {
    private static final BreederManager recManager = BreederManager.INSTANCE;

    private static final int parent1 = 0;
    private static final int parent2 = 1;
    private static final int pattern = 2;
    private static final int food = 3;
    private static final int output = 4;


    @Override
    public void initTile() {
        super.initTile();
        this.storage.setCapacity(240000);
        this.storage.setMaxReceive(240);
        this.storage.setMaxExtract(240000);
        this.RFTick = 5;
    }

    @Override
    protected IRecipe getRecipeIfCanStart() {
        if (inventory[parent1] == null || inventory[parent2] == null || inventory[food] == null || inventory[pattern] == null) {
            return null;
        }

        if (inventory[pattern].getItem() != ModItems.itemBlankPattern) {
            return null;
        }

        BreederManager.BreederRecipe recipe = recManager.getRecipeIfValid(inventory[parent1], inventory[parent2], inventory[food]);
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
        if (inventory[parent1] == null || inventory[parent2] == null || inventory[food] == null || inventory[pattern] == null) {
            return false;
        }

        BreederManager.BreederRecipe recipe = recManager.getRecipeIfValid(inventory[parent1], inventory[parent2], inventory[food]);
        return recipe != null && recipe.getCode() == recipeCode;
    }

    @Override
    protected void finishProcess() {
        BreederManager.BreederRecipe recipe = recManager.getRecipeIfValid(inventory[parent1], inventory[parent2], inventory[food]);
        increaseStackSize(output, recipe.getOutput());
        decrStackSize(food, recipe.getItemFoodEqualsTo(inventory[food]).stackSize);
        decrStackSize(pattern, 1);
    }

    @Override
    protected int getNumInput() {
        return 4;
    }

    @Override
    protected int getNumOutput() {
        return 1;
    }


    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        if (!this.invHelper.isInput(slot)) {
            return false;
        }

        if ((slot == parent1) || (slot == parent2)) {
            return itemstack.getItem() == ModItems.itemDigitalizedAnimal;
        }
        if (slot == pattern){
            return itemstack.getItem() == ModItems.itemBlankPattern;
        }

        if (slot == food) {
            return Animals.getFoodsSet().contains(itemstack.getItem());
        }
        return false;
    }

}
