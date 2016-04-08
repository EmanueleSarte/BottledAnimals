package com.ermans.bottledanimals.block.machine;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyStorage;
import com.ermans.bottledanimals.api.ITileEnergyInfo;
import com.ermans.bottledanimals.block.TileInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TilePowered extends TileInventory implements IEnergyReceiver, ITileEnergyInfo {

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
    private int lastEnergyInOld;
    protected int lastEnergyIn;



    @Override
    public void initTile() {
        super.initTile();
        this.storage = new EnergyStorage(0);
        this.RFTick = DF_ENERGY_CAPACITY / 3200; //default: 10RF/t
    }

    @Override
    public void update() {
        super.update();
        if (worldObj.isRemote){
            return;
        }
        lastEnergyIn = lastEnergyInOld;
        lastEnergyInOld = 0;

    }

    protected void modifyEnergyStored(int energy) {
        storage.modifyEnergyStored(energy);
    }


    //////////////////////IENERGYRECEIVER/////////////////////
    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        int energy = storage.receiveEnergy(maxReceive, simulate);
        if (!worldObj.isRemote && !simulate){
            lastEnergyInOld += energy;
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


    //////////////////////////////CLIENT///////////////////////////////////
    //////////////////////////////ITILEENERGYINFO//////////////////////////
    @SideOnly(Side.CLIENT)
    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return storage;
    }

    ////////////////////DATA SYNC/////////////////////////////
    @Override
    public int getField(int id) {
        switch (id){
            case 1:
                return storage.getEnergyStored();
            case 2:
                return lastEnergyIn;
        }
        return super.getField(id);
    }

    @Override
    public void setField(int id, int value) {
        switch (id){
            case 1:
                storage.setEnergyStored(value);
                return;
            case 2:
                lastEnergyIn = value;
                return;
        }
        super.setField(id, value);
    }

    @Override
    public int getFieldCount() {
        return super.getFieldCount() + 2;
    }

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
