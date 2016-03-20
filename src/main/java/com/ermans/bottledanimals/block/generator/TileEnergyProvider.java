package com.ermans.bottledanimals.block.generator;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyStorage;
import com.ermans.bottledanimals.block.IEnergyBA;
import com.ermans.bottledanimals.block.machine.TileInventory;
import com.ermans.bottledanimals.helper.TargetPointHelper;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.network.message.MessageEnergy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEnergyProvider extends TileInventory implements IEnergyProvider, IEnergyBA {

    protected static final int MAX_RF = 80;
    protected static final int CAPACITY = 32000;

    public boolean[][] DF_VALID_SIDE = new boolean[][]{
            {false, true, true, true, true, true},
            {true, false, true, true, true, true},
            {true, true, false, true, true, true},
            {true, true, true, false, true, true},
            {true, true, true, true, false, true},
            {true, true, true, true, true, false},
    };

    protected EnergyStorage storage;
    protected int lastOutEnergy;
    protected boolean doSync;

    @Override
    public void initTile() {
        super.initTile();
        this.storage = new EnergyStorage(CAPACITY,CAPACITY,MAX_RF);
    }


    @Override
    public void updateEntity() {
        if (!worldObj.isRemote && doSync) {
            doSync = false;
            PacketHandler.INSTANCE.sendToAllAround(new MessageEnergy(this.xCoord, this.yCoord, this.zCoord, this.storage.getEnergyStored()), TargetPointHelper.getTargetPoint(this));
        }
    }



    protected void modifyEnergyStored(int energy) {
        if (!worldObj.isRemote && energy != 0) {
            doSync = true;
        }
        storage.modifyEnergyStored(energy);
    }


    @Override
    public IEnergyStorage getEnergyStorage() {
        return storage;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return DF_VALID_SIDE[from.ordinal()][facing];
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        if (!worldObj.isRemote && maxExtract > 0 && !simulate){
            doSync = true;
        }
        return lastOutEnergy = storage.extractEnergy(maxExtract, simulate);
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