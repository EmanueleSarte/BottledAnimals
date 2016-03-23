package com.ermans.bottledanimals.block.generator;

import cofh.api.energy.IEnergyReceiver;
import com.ermans.api.IEnergyInfoProvider;
import com.ermans.bottledanimals.helper.BlockPos;
import com.ermans.bottledanimals.helper.BlockPosHelper;
import com.ermans.bottledanimals.helper.TargetPointHelper;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.network.message.MessageTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileGenerator extends TileEnergyProvider implements IEnergyInfoProvider {

    protected int remaining;
    protected int totalFuel;
    protected int actualRate;
    protected boolean isActive;

    protected int lastEnergyOut;


    protected STATE state;
    public enum STATE {
        LOW_GEN,
        BALANCING,
        RIGHT_GEN,
        OFF
    }


    @Override
    public void initTile() {
        super.initTile();
        actualRate = 0;
        state = STATE.OFF;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        boolean sendUpdate = false;
        boolean hasChanged = isActive;
        STATE stateChanged = state;
        int actualRateVar = actualRate;

        if (!worldObj.isRemote) {

            if (remaining > 0) {

                int energyGenerated = maxRF;

                if (lastEnergyOut == 0) {
                    if (isStorageHalfFull()) {
                        actualRate--;
                        if (actualRate <= 0){
                            actualRate = 1;
                        }
                        energyGenerated = actualRate;
                        state = STATE.LOW_GEN;
                    } else {
                        actualRate = energyGenerated;
                        state = STATE.BALANCING;
                    }
                } else {
                    if (isStorageHalfFull()) {

                        if (actualRate > lastEnergyOut){
                            actualRate--;
                            if (actualRate < lastEnergyOut){
                                actualRate = lastEnergyOut;
                            }
                        }else{
                            actualRate++;
                            if (actualRate > lastEnergyOut){
                                actualRate = lastEnergyOut;
                            }
                        }
                        state = STATE.RIGHT_GEN;
                        energyGenerated = actualRate;
                    } else {
                        actualRate = energyGenerated;
                        state = STATE.BALANCING;
                    }
                }

                energyGenerated = Math.min(energyGenerated, remaining);
                remaining -= energyGenerated;
                modifyEnergyStored(energyGenerated);

                if (remaining <= 0) {
                    isActive = false;
                    sendUpdate = true;
                    state = STATE.OFF;
                }

            } else {

                if (hasPassedRedstoneTest()) {
                    if (!isStorageFull()) {
                        if (canStart()) {
                            remaining = totalFuel = startProcess();
                            sendUpdate = true;
                            isActive = true;
                        }
                    }
                }
                if (actualRate != 0) {
                    actualRate = 0;
                }
            }


            lastEnergyOut = 0;
            if (hasPassedRedstoneTest() && !isStorageEmpty()) {

                for (int i = 0; !isStorageEmpty() && lastEnergyOut < maxRF && i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
                    ForgeDirection fd = ForgeDirection.VALID_DIRECTIONS[i];
                    if (canConnectEnergy(fd)) {
                        BlockPos bp = BlockPosHelper.getBlockAdjacent(xCoord, yCoord, zCoord, fd);
                        if (bp != null) {
                            TileEntity tile = worldObj.getTileEntity(bp.x, bp.y, bp.z);
                            if (tile instanceof IEnergyReceiver) {
                                int energy = ((IEnergyReceiver) tile).receiveEnergy(fd, extractEnergy(fd, Math.min(maxRF, maxRF - lastEnergyOut), true), false);
                                lastEnergyOut += extractEnergy(fd, energy, false);
                            }
                        }
                    }
                }
            }
        }

        if (actualRateVar != actualRate || stateChanged != state) {
            sendUpdate = true;
        }

        if (sendUpdate || isActive && checkTick(4)) {
            syncMachine(hasChanged != isActive);
        }
    }


    protected void syncMachine(boolean updateTexture) {
        //this is called clientside (I mean the message) to update the texture
        PacketHandler.INSTANCE.sendToAllAround(new MessageTile(this, updateTexture), TargetPointHelper.getTargetPoint(this));
        markDirty();
        //This is called server side to update light
        if (updateTexture) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    protected boolean isStorageHalfFull() {
        return getEnergyStored(null) * 2 >= getMaxEnergyStored(null);
    }

    protected boolean isStorageFull() {
        return getMaxEnergyStored(null) == getEnergyStored(null);
    }

    protected boolean isStorageEmpty() {
        return getEnergyStored(null) <= 0;
    }

    abstract protected boolean canStart();

    //Return the fuelValue
    abstract protected int startProcess();


    public STATE getState(){
        return state;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public int getInfoFuelPercentage() {
        if (!isActive || remaining == 0) return 0;
        return remaining * 100 / totalFuel;
    }

    @Override
    public int getInfoEnergyPerTick() {
        return actualRate;
    }

    @Override
    public int getInfoMaxEnergyPerTick() {
        return storage.getMaxExtract();
    }

    @Override
    public int getInfoEnergyStored() {
        return storage.getEnergyStored();
    }

    @Override
    public int getInfoMaxEnergyStored() {
        return storage.getMaxEnergyStored();
    }

    @Override
    public int getEnergyDrainPerTick() {
        return lastEnergyOut;
    }

    @SideOnly(Side.CLIENT)
    public int getFuelScaled(int scale) {
        if (!isActive || remaining == 0) return 0;
        return remaining * scale / totalFuel;
    }


    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        remaining = nbt.getInteger("remaining");
        totalFuel = nbt.getInteger("totalFuel");
        actualRate = nbt.getInteger("actualRate");
        state = STATE.values()[nbt.getByte("state")];
        isActive = nbt.getBoolean("isActive");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("remaining", remaining);
        nbt.setInteger("totalFuel", totalFuel);
        nbt.setInteger("actualRate", actualRate);
        nbt.setByte("state", (byte) state.ordinal());
        nbt.setBoolean("isActive", isActive);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeInt(remaining);
        buf.writeInt(totalFuel);
        buf.writeInt(actualRate);
        buf.writeInt(lastEnergyOut);
        buf.writeByte(state.ordinal());
        buf.writeBoolean(isActive);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        remaining = buf.readInt();
        totalFuel = buf.readInt();
        actualRate = buf.readInt();
        lastEnergyOut = buf.readInt();
        state = STATE.values()[buf.readByte()];
        isActive = buf.readBoolean();
    }


}

