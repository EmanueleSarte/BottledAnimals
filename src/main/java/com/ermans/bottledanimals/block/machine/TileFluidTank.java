package com.ermans.bottledanimals.block.machine;


import com.ermans.bottledanimals.helper.TargetPointHelper;
import com.ermans.bottledanimals.init.ModFluids;
import com.ermans.bottledanimals.init.ModItems;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.network.message.MessageFluid;
import com.ermans.repackage.cofh.lib.util.helpers.FluidHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public abstract class TileFluidTank extends TileMachine implements IFluidHandler {

    public FluidTank tank;

    private boolean doSync;
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
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            if (doSync) {
                doSync = false;
                PacketHandler.INSTANCE.sendToAllAround(new MessageFluid(this, getFluidName()), TargetPointHelper.getTargetPoint(this));
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
    }

    private void markDirtyFluid() {
        doSync = true;
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

    protected void modifyFluidAmount(Fluid fluid, int amount) {

        if (amount > 0) {
            tank.fill(new FluidStack(fluid, amount), true);
        } else if (amount < 0) {
            tank.drain(-1 * amount, true);
        }
        if (!worldObj.isRemote && amount != 0) {
            markDirtyFluid();
        }
    }


    protected int getTankFreeSpace() {
        return tank.getCapacity() - tank.getFluidAmount();
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
//        if (transferFluid && !isTankEmpty() && drainSlotInput != -1) {
//            if (itemStack != null && itemStack.getItem() == Items.bucket && tank.getFluidAmount() >= FluidContainerRegistry.BUCKET_VOLUME) {
//                ItemStack filledContainer = FluidHelper.getFluidContainerData(fluidTile, itemStack).filledContainer;
//                filledContainer.stackSize = 1;
//                ItemHelper.decreaseStackSize(itemStack, 1);
//                ItemHelper.addItemStackToPlayer(player, filledContainer, true);
//                modifyFluidAmount(fluidTile, -1 * FluidContainerRegistry.BUCKET_VOLUME);
//                player.inventoryContainer.detectAndSendChanges();
//                return true;
//            }
//        }
        return false;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

        if (resource == null || !canFill(from, resource.getFluid())) {
            return 0;
        }

        int res = tank.fill(resource, doFill);

        if (!worldObj.isRemote && res > 0 && doFill) {
            markDirtyFluid();
        }

        return res;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (tank.getFluid() == null || resource == null || !canDrain(from, resource.getFluid()) || !resource.isFluidEqual(tank.getFluid())) {
            return null;
        }

        FluidStack res = tank.drain(resource.amount, doDrain);
        if (!worldObj.isRemote && doDrain && res.amount > 0) {
            markDirtyFluid();
        }
        return res;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

        if (tank.getFluid() == null || !canDrain(from, this.fluidTile)) {
            return null;
        }
        FluidStack res = tank.drain(maxDrain, doDrain);
        if (!worldObj.isRemote && doDrain && res.amount > 0) {
            markDirtyFluid();
        }
        return res;
    }

    protected int getFluidAmount() {
        return tank.getFluidAmount();
    }

    protected int getTankCapacity() {
        return tank.getCapacity();
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{tank.getInfo()};
    }

    public FluidTank getFluidTank() {
        return tank;
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
