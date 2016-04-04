package com.ermans.bottledanimals.block.machine;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyStorage;
import com.ermans.api.IEnergyBA;
import com.ermans.bottledanimals.block.TileInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TilePowered extends TileInventory implements IEnergyReceiver, IEnergyBA {

    public static final int DF_ENERGY_CAPACITY = 32000;
    public static final int DF_ENERGY_MAX_RCV = 32;
    //we are an EnergyReceiver,but we want the possibility to drain energy without limits for our things :D
    public static final int DF_ENERGY_MAX_EXT = DF_ENERGY_CAPACITY;


    //Based on facing.  Btw 0 and 1 indexes aren't used because facing = [2,5]
    public boolean[][] DF_VALID_SIDE = new boolean[][]{
            {false, true, true, true, true, true},
            {true, false, true, true, true, true},
            {true, true, false, true, true, true},
            {true, true, true, false, true, true},
            {true, true, true, true, false, true},
            {true, true, true, true, true, false},
    };

    //We don't want to force tiles who implements this class to have a storage, use initTile to setup the storage...
    protected EnergyStorage storage;
    protected int RFTick;


    @Override
    public void initTile() {
        super.initTile();
        this.storage = new EnergyStorage(0);
        this.RFTick = DF_ENERGY_CAPACITY / 3200; //default: 10RF/t
    }

    private void syncEnergy(){
        worldObj.addBlockEvent(pos,getBlockType(), 100, storage.getEnergyStored());
    }

    @Override
    public void setEnergyStored(int amount) {
        this.storage.setEnergyStored(amount);
    }


    @Override
    public IEnergyStorage getEnergyStorage() {
        return this.storage;
    }



    @SideOnly(Side.CLIENT)
    public int getScaledEnergyStored(int scale) {
        return getEnergyStored(null) * scale / getMaxEnergyStored(null);
    }


    protected void modifyEnergyStored(int energy) {
        if (!worldObj.isRemote && energy != 0) {
            syncEnergy();
        }
        storage.modifyEnergyStored(energy);
    }

    ////IEnergyReceiver///
    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        int energy = storage.receiveEnergy(maxReceive, simulate);
        if (!worldObj.isRemote && energy > 0 && !simulate) {
            syncEnergy();
        }
        return energy;
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return DF_VALID_SIDE[from.ordinal()][facing.ordinal()];
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
    public boolean receiveClientEvent(int id, int value) {
        if (id == 100){
            storage.setEnergyStored(value);
            return true;
        }
        return super.receiveClientEvent(id, value);
    }

    //NBT
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        storage.readFromNBT(nbtTagCompound);

    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        storage.writeToNBT(nbtTagCompound);
    }
}
