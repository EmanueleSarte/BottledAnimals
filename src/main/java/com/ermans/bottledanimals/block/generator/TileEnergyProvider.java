package com.ermans.bottledanimals.block.generator;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyStorage;
import com.ermans.api.IEnergyBA;
import com.ermans.bottledanimals.block.machine.TileInventory;
import com.ermans.bottledanimals.helper.TargetPointHelper;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.network.message.MessageEnergy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public abstract class TileEnergyProvider extends TileInventory implements IEnergyProvider, IEnergyBA {

    private static final int MAX_RF = 80;
    private static final int CAPACITY = 64000;

    public boolean[][] DF_VALID_SIDE = new boolean[][]{
            {false, true, true, true, true, true},
            {true, false, true, true, true, true},
            {true, true, false, true, true, true},
            {true, true, true, false, true, true},
            {true, true, true, true, false, true},
            {true, true, true, true, true, false},
    };

    protected EnergyStorage storage;

    protected boolean doSync;

    protected int maxRF;
    protected int capacity;


    @Override
    public void initTile() {
        super.initTile();
        maxRF = MAX_RF;
        capacity = CAPACITY;
        this.storage = new EnergyStorage(capacity, capacity, maxRF);
    }


    @Override
    public void update() {
        if (!worldObj.isRemote) {
            if (doSync) {
                doSync = false;
                PacketHandler.INSTANCE.sendToAllAround(new MessageEnergy(pos.getX(), pos.getY(), pos.getZ(),this.storage.getEnergyStored()), TargetPointHelper.getTargetPoint(this));
            }
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
    public boolean canConnectEnergy(EnumFacing from) {
        return DF_VALID_SIDE[from.ordinal()][facing.ordinal()];
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        if (!worldObj.isRemote && maxExtract > 0 && !simulate) {
            doSync = true;
        }
        return storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
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