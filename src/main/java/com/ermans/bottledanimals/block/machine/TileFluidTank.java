package com.ermans.bottledanimals.block.machine;


import com.ermans.bottledanimals.helper.TargetPointHelper;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.network.message.MessageFluid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public abstract class TileFluidTank extends TileMachine implements IFluidHandler {

    public FluidTank tank;

    private boolean doSync;

    protected Fluid fluidTile;

    protected int getFluidAmount() {
        return tank.getFluidAmount();
    }

    protected int getTankCapacity() {
        return tank.getCapacity();
    }

    @Override
    public void initTile() {
        super.initTile();
        this.tank = new FluidTank(0);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote && doSync) {
            doSync = false;
            PacketHandler.INSTANCE.sendToAllAround(new MessageFluid(this, getFluidName()), TargetPointHelper.getTargetPoint(this));
        }
    }

    private void markDirtyFluid() {
        doSync = true;
    }

    public String getFluidName() {
        return fluidTile.getName();
    }

    protected boolean hasTankEnoughSpace(int amout) {
        return tank.getFluidAmount() + amout <= tank.getCapacity();
    }

    protected void modifyFluidAmount(Fluid fluid, int amount) {

        if (amount > 0) {
            tank.fill(new FluidStack(fluid, amount), true);
        } else if (amount < 0) {
            tank.drain(-1 * amount, true);
        }
        if (!worldObj.isRemote && amount != 0){
            markDirtyFluid();
        }
    }


    protected boolean isTankFull(){
        return tank.getFluidAmount() >= tank.getCapacity();
    }

    protected int getTankFreeSpace(){
        return tank.getCapacity() - tank.getFluidAmount();
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
