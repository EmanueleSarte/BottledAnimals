package com.ermans.bottledanimals.block.generator;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import com.ermans.bottledanimals.block.IEnergyBA;
import com.ermans.bottledanimals.block.machine.TileInventory;
import com.ermans.bottledanimals.helper.TargetPointHelper;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.network.message.MessageEnergy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEnergyProvider extends TileInventory implements IEnergyProvider, IEnergyBA {

    public boolean[][] DF_VALID_SIDE = new boolean[][]{
            {false, true, true, true, true, true},
            {true, false, true, true, true, true},
            {true, true, false, true, true, true},
            {true, true, true, false, true, true},
            {true, true, true, true, false, true},
            {true, true, true, true, true, false},
    };

    protected EnergyStorage storage = new EnergyStorage(32000);



    protected void syncEnergy(){
        PacketHandler.INSTANCE.sendToAllAround(new MessageEnergy(this.xCoord, this.yCoord, this.zCoord, this.storage.getEnergyStored()), TargetPointHelper.getTargetPoint(this));
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return DF_VALID_SIDE[from.ordinal()][facing];
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        if (!worldObj.isRemote && maxExtract > 0 && !simulate){
            syncEnergy();
        }
        return storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return storage.getMaxEnergyStored();
    }


    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        storage.writeToNBT(nbt);
    }


    @Override
    public void setEnergyStored(int amount) {
        storage.setEnergyStored(amount);
    }
}