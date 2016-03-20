package com.ermans.bottledanimals.block.machine;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyStorage;
import com.ermans.bottledanimals.block.IEnergyBA;
import com.ermans.bottledanimals.helper.TargetPointHelper;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.network.message.MessageEnergy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

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
    //...but you need to tell us when you want to. This field doesn't disable the storage, just don't allow machines/wires to connect this tileentity
    //You shouldn't switch that, just set on init, otherwise is a your problem
    protected boolean activeEnergyStorage = true;
    protected int RFTick;

    private boolean doSync;


    @Override
    public void initTile() {
        super.initTile();
        this.storage = new EnergyStorage(0);
        this.RFTick = DF_ENERGY_CAPACITY / 3200; //default: 10RF/t
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote && doSync) {
            doSync = false;
            PacketHandler.INSTANCE.sendToAllAround(new MessageEnergy(this.xCoord, this.yCoord, this.zCoord, this.storage.getEnergyStored()), TargetPointHelper.getTargetPoint(this));
        }
    }

    private void markDirtyEnergy(){
        doSync = true;
    }

    @Override
    public void setEnergyStored(int amount) {
        this.storage.setEnergyStored(amount);
    }


    public IEnergyStorage getEnergyStorage() {
        return this.storage;
    }

    public int getMaxReceiveEnergy(){
        return storage.getMaxReceive();
    }


    @SideOnly(Side.CLIENT)
    public int getScaledEnergyStored(int scale) {
        return getEnergyStored(null) * scale / getMaxEnergyStored(null);
    }


    protected void modifyEnergyStored(int energy) {
        if (!worldObj.isRemote && energy != 0) {
            markDirtyEnergy();
        }
        storage.modifyEnergyStored(energy);
    }

    ////IEnergyReceiver///
    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        int energy = storage.receiveEnergy(maxReceive, simulate);
        if (!worldObj.isRemote && energy > 0 && !simulate) {
            markDirtyEnergy();
        }
        return energy;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return activeEnergyStorage && DF_VALID_SIDE[from.ordinal()][facing];
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return storage.getMaxEnergyStored();
    }


    //NBT
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        activeEnergyStorage = nbtTagCompound.getBoolean("activeEnergy");
        //Idk if this can cause problems
        if (activeEnergyStorage) storage.readFromNBT(nbtTagCompound);

    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("activeEnergy", activeEnergyStorage);
        //Idk if this can cause problems
        if (activeEnergyStorage) storage.writeToNBT(nbtTagCompound);
    }
}
