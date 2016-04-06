package com.ermans.bottledanimals.block.machine.rancher;

import com.ermans.bottledanimals.block.machine.TileFluidTank;
import com.ermans.bottledanimals.init.ModFluids;
import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.recipe.IRecipe;
import com.ermans.bottledanimals.recipe.RancherManager;
import com.ermans.repackage.cofh.lib.util.helpers.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileRancher extends TileFluidTank {

    private static final RancherManager recManager = RancherManager.INSTANCE;

    public static final int TANK_CAPACITY = 10 * FluidContainerRegistry.BUCKET_VOLUME;

    private static final int animal = 0;
    private static final int gear = 1;
    private static final int fluidInput = 2;
    private static final int output = 3;
    private static final int fluidOutput = 4;


    @Override
    public void initTile() {
        super.initTile();
        this.storage.setCapacity(DF_ENERGY_CAPACITY);
        this.storage.setMaxReceive(DF_ENERGY_MAX_RCV);
        this.storage.setMaxExtract(DF_ENERGY_MAX_EXT);
        this.RFTick = 4;

        this.tank.setCapacity(TANK_CAPACITY);
        this.fluidTile = ModFluids.milk;

        this.transferFluid = true;
        this.drainSlotInput = fluidInput;
        this.drainSlotOutput = fluidOutput;
    }


    @Override
    public boolean canFill(EnumFacing from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid) {
        return DF_VALID_SIDE[from.ordinal()][facing.ordinal()] && tank.getFluid() != null;
    }

    @Override
    protected IRecipe getRecipeIfCanStart() {
        if (inventory[animal] == null || inventory[gear] == null || inventory[gear].getItem() != ModItems.itemRancherGear) {
            return null;
        }

        RancherManager.RancherRecipe recipe = recManager.getRecipeIfValid(inventory[animal]);
        if (recipe == null) {
            return null;
        }

        if (recipe.hasFluidOutput()) {
            if (!hasTankEnoughSpace(recipe.getFluidStackAmount())) {
                return null;
            }
        }

        if (recipe.getOutput() == null) {
            return recipe;
        }

        if (inventory[output] == null) {
            return recipe;
        }

        if (inventory[output].isItemEqual(recipe.getOutput()) && inventory[output].stackSize + recipe.getOutputStackSize() <= inventory[output].getMaxStackSize()) {
            return recipe;
        }
        return null;
    }


    @Override
    protected boolean canStillProcess(int recipeCode) {
        if (inventory[animal] == null || inventory[gear] == null) {
            return false;
        }

        RancherManager.RancherRecipe recipe = recManager.getRecipeIfValid(inventory[animal]);
        return recipe != null && recipe.getCode() == recipeCode;
    }

    @Override
    protected void finishProcess() {
        RancherManager.RancherRecipe recipe = recManager.getRecipeIfValid(inventory[animal]);
        if (recipe.getOutput() != null) {
            increaseStackSize(output, recipe.getOutput());
        }
        if (recipe.hasFluidOutput()) {
            modifyFluidAmount(fluidTile, recipe.getFluidStackAmount());
        }
        ItemHelper.damageStack(inventory[gear], 1);
    }

    @Override
    protected int getNumInput() {
        return 3;
    }

    @Override
    protected int getNumOutput() {
        return 2;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        if (!invHelper.isInput(slot)) {
            return false;
        }
        if (slot == gear) {
            return itemstack.getItem() == ModItems.itemRancherGear;
        }
        if (slot == animal) {
            return itemstack.getItem() == ModItems.itemDigitalizedAnimal;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int getProcessingAnimal() {
        if (inventory[animal] == null) {
            return -1;
        } else {
            return inventory[animal].getItemDamage();
        }
    }

}
