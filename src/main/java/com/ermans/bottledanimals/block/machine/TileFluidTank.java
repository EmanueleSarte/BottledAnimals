package com.ermans.bottledanimals.block.machine;


import com.ermans.bottledanimals.init.ModFluids;
import com.ermans.bottledanimals.init.ModItems;
import com.ermans.repackage.cofh.lib.util.helpers.FluidHelper;
import com.ermans.repackage.cofh.lib.util.helpers.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.*;

public abstract class TileFluidTank extends TileMachine implements IFluidHandler {

    public FluidTank tank;

    protected Fluid fluidTile;

    protected int drainSlotInput;
    protected int drainSlotOutput;
    protected int fillSlot;
    protected boolean transferFluid;

    private static int MAX_TRANSFER_RATE = FluidContainerRegistry.BUCKET_VOLUME;

    @Override
    public void initTile() {
        super.initTile();
        this.tank = new FluidTank(0);
        this.transferFluid = false;
        this.drainSlotInput = -1;
        this.drainSlotOutput = -1;
        this.fillSlot = -1;

    }

    @Override
    public void update() {
        super.update();
        if (worldObj.isRemote) {
            return;
        }

        if (transferFluid && checkTick(20)) {
            if (!isTankEmpty() && drainSlotInput != -1 && drainSlotOutput != -1) {
                drainFluidIntoItems(drainSlotInput, drainSlotOutput);
            }
            if (!isTankFull() && fillSlot != -1) {
                fillFluidFromItems(fillSlot);
            }
        }

    }

    protected void modifyFluidAmount(Fluid fluid, int amount) {
        if (amount > 0) {
            tank.fill(new FluidStack(fluid, amount), true);
        } else if (amount < 0) {
            tank.drain(-1 * amount, true);
        }
    }

    protected void fillFluidFromItems(int slot) {
        ItemStack itemStack = inventory[slot];
        if (itemStack != null) {
            if (FluidHelper.isFluidContainerItem(itemStack)) {

                IFluidContainerItem fluidContainer = (IFluidContainerItem) itemStack.getItem();
                if (FluidHelper.isFluidEqual(ModFluids.food, fluidContainer.getFluid(itemStack))) {

                    FluidStack drain = fluidContainer.drain(itemStack, Math.min(MAX_TRANSFER_RATE, getTankFreeSpace()), true);
                    modifyFluidAmount(drain.getFluid(), drain.amount);
                }
            } else if (hasTankEnoughSpace(MAX_TRANSFER_RATE)) {
                if (FluidHelper.hasFluidTag(itemStack)) {
                    if (FluidHelper.isFluidTagEquals(itemStack, ModFluids.food)) {
                        modifyFluidAmount(ModFluids.food, MAX_TRANSFER_RATE);
                        decrStackSize(slot, 1);
                    }
                } else if (itemStack.getItem() == ModItems.foodBucket) {
                    modifyFluidAmount(ModFluids.food, MAX_TRANSFER_RATE);
                    decrStackSize(slot, 64);
                    increaseStackSize(slot, new ItemStack(Items.bucket));
                }
            }
        }
    }

    protected void drainFluidIntoItems(int slotInput, int slotOutput) {
        ItemStack itemStack = inventory[slotInput];
        if (itemStack != null) {
            if (FluidHelper.isFluidContainerItem(itemStack)) {
                IFluidContainerItem fluidContainer = (IFluidContainerItem) itemStack.getItem();
                FluidStack fluidStack = fluidContainer.getFluid(itemStack);
                if (fluidStack == null || (fluidContainer.getCapacity(itemStack) - fluidStack.amount > 0 && fluidStack.getFluid() == fluidTile)) {
                    int filled = fluidContainer.fill(itemStack, new FluidStack(fluidTile, Math.min(MAX_TRANSFER_RATE, tank.getFluidAmount())), true);
                    modifyFluidAmount(fluidTile, filled);
                    FluidStack fluidStackAfterFill = fluidContainer.getFluid(itemStack);
                    if (fluidStackAfterFill.amount == fluidContainer.getCapacity(itemStack)) {
                        ItemStack copy = inventory[slotInput].copy();
                        copy.stackSize = 1;
                        decrStackSize(slotInput, 1);
                        increaseStackSize(slotOutput, copy);
                    }
                }
            } else {
                FluidContainerRegistry.FluidContainerData containerData = FluidHelper.getFluidContainerData(fluidTile, itemStack);
                if (containerData != null && getFluidAmount() >= containerData.fluid.amount) {
                    if (inventory[slotOutput] == null ||
                            (inventory[slotOutput].isItemEqual(containerData.filledContainer) &&
                                    inventory[slotOutput].stackSize + containerData.filledContainer.stackSize <= inventory[slotOutput].getMaxStackSize())) {

                        modifyFluidAmount(fluidTile, -1 * containerData.fluid.amount);
                        decrStackSize(slotInput, 1);
                        increaseStackSize(slotOutput, containerData.filledContainer);
                    }
                }
            }
        }
    }


    @Override
    public boolean handleRightClick(EntityPlayer player, ItemStack itemStack, float xClicked, float yClicked, float zClicked) {
        if (transferFluid && !isTankEmpty() && drainSlotInput != -1) {
            if (itemStack != null && itemStack.getItem() == Items.bucket && tank.getFluidAmount() >= MAX_TRANSFER_RATE) {
                ItemStack filledContainer = FluidHelper.getFluidContainerData(fluidTile, itemStack).filledContainer.copy();
                filledContainer.stackSize = 1;
                if (itemStack.stackSize == 1) {
                    player.inventory.mainInventory[player.inventory.currentItem] = filledContainer;
                } else {
                    ItemHelper.decreaseStackSize(itemStack, 1);
                    ItemHelper.addItemStackToPlayer(player, filledContainer, true);
                }
                modifyFluidAmount(fluidTile, -1 * MAX_TRANSFER_RATE);
                player.inventoryContainer.detectAndSendChanges();
                return true;
            }
        }
        return false;
    }

    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill) {

        if (resource == null || !canFill(from, resource.getFluid())) {
            return 0;
        }
        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
        if (tank.getFluid() == null || resource == null || !canDrain(from, resource.getFluid()) || !resource.isFluidEqual(tank.getFluid())) {
            return null;
        }

        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
        if (tank.getFluid() == null || !canDrain(from, this.fluidTile)) {
            return null;
        }

        return tank.drain(maxDrain, doDrain);
    }

    public String getFluidName() {
        return fluidTile.getName();
    }

    protected boolean hasTankEnoughSpace(int amount) {
        return tank.getFluidAmount() + amount <= tank.getCapacity();
    }

    protected boolean isTankEmpty() {
        return tank.getFluidAmount() == 0;
    }

    protected boolean isTankFull() {
        return tank.getFluidAmount() >= tank.getCapacity();
    }

    protected int getTankFreeSpace() {
        return tank.getCapacity() - tank.getFluidAmount();
    }

    protected int getFluidAmount() {
        return tank.getFluidAmount();
    }

    protected int getTankCapacity() {
        return tank.getCapacity();
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from) {
        return new FluidTankInfo[]{tank.getInfo()};
    }

    public FluidTank getFluidTank() {
        return tank;
    }


    //////////////////////DATA SYNC/////////////////////////
    @Override
    public int getField(int id) {
        if (id == 5) {
            return tank.getFluidAmount();
        }
        return super.getField(id);
    }

    @Override
    public void setField(int id, int value) {
        if (id == 5) {
            tank.setFluid(new FluidStack(fluidTile, value));
            return;
        }
        super.setField(id, value);
    }

    @Override
    public int getFieldCount() {
        return super.getFieldCount() + 1;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        tank.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tank.writeToNBT(tag);
    }


}
