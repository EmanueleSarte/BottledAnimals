package com.ermans.bottledanimals.block.machine.foodcrusher;

import com.ermans.bottledanimals.block.machine.TileFluidTank;
import com.ermans.bottledanimals.helper.FoodHelper;
import com.ermans.bottledanimals.init.ModFluids;
import com.ermans.bottledanimals.recipe.FoodCrusherManager;
import com.ermans.bottledanimals.recipe.IRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class TileFoodCrusher extends TileFluidTank {

    private FoodCrusherManager recManager = FoodCrusherManager.INSTANCE;

    private static final int TANK_CAPACITY = 10 * FluidContainerRegistry.BUCKET_VOLUME;

    private static final int RECIPE_TIME = 100;
    private static final int input = 0;
    private static final int foodContainer = 1;


    @Override
    public void initTile() {
        super.initTile();
        this.storage.setCapacity(DF_ENERGY_CAPACITY);
        this.storage.setMaxReceive(DF_ENERGY_MAX_RCV);
        this.storage.setMaxExtract(DF_ENERGY_MAX_EXT);

        this.tank.setCapacity(TANK_CAPACITY);
        this.fluidTile = ModFluids.food;
    }

    @Override
    protected int getNumInput() {
        return 1;
    }

    @Override
    protected int getNumOutput() {
        return 1;
    }


    @Override
    protected IRecipe getRecipeIfCanStart() {
        if (inventory[input] == null) return null;

        FoodCrusherManager.FoodCrusherRecipe recipe = recManager.getRecipeIfValid(inventory[input]);

        if (recipe == null){
            return null;
        }



        if (recipe.getFoodAmount() + this.getFluidAmount() > this.getTankCapacity()) {
            return null;
        }

        if (inventory[foodContainer] == null) {
            return recipe;
        }

        Item container = inventory[input].getItem().getContainerItem();
        if (container == null) {
            return recipe;
        }
        if (inventory[foodContainer].isStackable() && inventory[foodContainer].getItem() == container &&
                inventory[foodContainer].stackSize + 1 <= inventory[foodContainer].getMaxStackSize()){
            return recipe;
        }
        return null;
    }


    @Override
    protected boolean canStillProcess(int recipeCode) {
        if (inventory[input] == null){
            return false;
        }
        FoodCrusherManager.FoodCrusherRecipe recipe = recManager.getRecipeIfValid(inventory[input]);
        return recipe != null && recipe.getCode() == recipeCode;
    }

    private int getFoodValue(ItemStack itemStack) {
        return (int) (FoodHelper.getFoodValue(itemStack) * 100);
    }

    @Override
    protected void finishProcess() {
        modifyFluidAmount(fluidTile, getFoodValue(inventory[input]));

        if (inventory[input].getItem().hasContainerItem(inventory[input])) {
            increaseStackSize(foodContainer, new ItemStack(inventory[input].getItem().getContainerItem()));
        } else {
            if (inventory[input].getItem() == Items.mushroom_stew) {
                increaseStackSize(foodContainer, new ItemStack(Items.bowl));
            }
        }

        decrStackSize(input, 1);
    }


    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        if (!this.invHelper.isInput(slot)) {
            return false;
        }
        return FoodHelper.getFoodValue(itemstack) > 0;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return DF_VALID_SIDE[from.ordinal()][facing] && tank.getFluid() != null;
    }
}
