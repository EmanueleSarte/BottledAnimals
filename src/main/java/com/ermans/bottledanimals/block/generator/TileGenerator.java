package com.ermans.bottledanimals.block.generator;

import cofh.api.energy.IEnergyReceiver;
import com.ermans.api.IEnergyInfoProvider;
import com.ermans.bottledanimals.helper.BlockPosHelper;
import com.ermans.bottledanimals.helper.TargetPointHelper;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.network.message.MessageTile;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

public abstract class TileGenerator extends TileEnergyProvider implements IEnergyInfoProvider {

    protected int remaining;
    protected int totalFuel;
    protected int actualRate;
    protected boolean isActive;

    protected int lastEnergyOut;

    private Map<EnumFacing, IEnergyReceiver> receiversMap;


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
    public void update() {
        super.update();

        boolean sendUpdate = false;
        boolean hasChanged = isActive;

        if (!worldObj.isRemote) {

            if (remaining > 0) {

                int energyGenerated = maxRF;

                if (lastEnergyOut == 0) {
                    if (isStorageHalfFull()) {
                        actualRate--;
                        if (actualRate <= 0) {
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

                        if (actualRate > lastEnergyOut) {
                            actualRate--;
                            if (actualRate < lastEnergyOut) {
                                actualRate = lastEnergyOut;
                            }
                        } else {
                            actualRate++;
                            if (actualRate > lastEnergyOut) {
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
                }

            }

            if (remaining <= 0) {
                if (hasPassedRedstoneTest()) {
                    if (!isStorageFull() && canStart()) {
                        remaining = totalFuel = startProcess();
                        sendUpdate = true;
                        isActive = true;
                    }
                }
                if (!isActive) {
                    state = STATE.OFF;
                    if (actualRate != 0) {
                        actualRate = 0;
                    }
                }
            }


            lastEnergyOut = 0;
            if (hasPassedRedstoneTest() && !isStorageEmpty()) {

                if (receiversMap == null) {
                    updateReceivers();
                }

//                for (int i = 0; !isStorageEmpty() && lastEnergyOut < maxRF && i < cachedReceivers.length; i++) {
////                    EnumFacing ef = EnumFacing.values()[i];
////                    if (canConnectEnergy(ef)) {
////                        BlockPos bp = BlockPosHelper.getBlockAdjacent(getPos(), ef);
////                        if (bp != null) {
////                            TileEntity tile = worldObj.getTileEntity(bp);
////                            if (tile instanceof IEnergyReceiver) {
////                                int energy = ((IEnergyReceiver) tile).receiveEnergy(ef, extractEnergy(ef, Math.min(maxRF, maxRF - lastEnergyOut), true), false);
////                                lastEnergyOut += extractEnergy(ef, energy, false);
////                            }
////                        }
////                    }
//                    if (cachedReceivers[i] != null) {
//                        EnumFacing face = EnumFacing.values()[i];
//                        int energy = cachedReceivers[i].receiveEnergy(face, extractEnergy(face, Math.min(maxRF, maxRF - lastEnergyOut), true), false);
//                        lastEnergyOut += extractEnergy(face, energy, false);
//                    }
//                }

                int good = receiversMap.size();
                int badFaces = 0;
                while (good > 0 && lastEnergyOut < maxRF && !isStorageFull()) {

                    int actualAverage = (maxRF - lastEnergyOut) / good;
                    int odd = (maxRF - lastEnergyOut) - actualAverage * good;

                    good = 0;
                    int counter = 1;
                    for (EnumFacing face : receiversMap.keySet()) {
                        IEnergyReceiver receiver = receiversMap.get(face);
                        if ((badFaces & counter) != counter) {

                            int energy = receiver.receiveEnergy(face, extractEnergy(face, actualAverage + odd, true), false);
                            lastEnergyOut += extractEnergy(face, energy, false);

                            if (energy == actualAverage + odd && receiver.receiveEnergy(face, 1, true) > 0) {
                                good++;
                            }else{
                                badFaces = badFaces | counter;
                            }
                            odd = 0;
                        }
                        counter = counter * 2;
                    }
                }
            }
        }

        if (sendUpdate) {
            syncMachine(hasChanged != isActive);
        }
    }


    protected void syncMachine(boolean updateTexture) {

        PacketHandler.INSTANCE.sendToAllAround(new MessageTile(this, updateTexture), TargetPointHelper.getTargetPoint(worldObj, pos));
        markDirty();

        if (updateTexture) {
            worldObj.markBlockForUpdate(pos);
        }
    }

    protected void updateReceivers() {
//        cachedReceivers.clear();
//        facingReceivers.clear();
//
//        for (EnumFacing face : EnumFacing.values()) {
//            if (canConnectEnergy(face)) {
//                BlockPos adjacent = BlockPosHelper.getBlockAdjacent(pos, face);
//                if (adjacent != null) {
//                    TileEntity entity = worldObj.getTileEntity(adjacent);
//                    if (entity != null && entity instanceof IEnergyReceiver) {
//                        cachedReceivers.add((IEnergyReceiver) entity);
//                        facingReceivers.add(face);
//                    }
//                }
//            }
//        }

        if (facing == null){
            return;
        }
        receiversMap = new HashMap<EnumFacing, IEnergyReceiver>();
        for (EnumFacing face : EnumFacing.values()) {
            if (canConnectEnergy(face)) {
                BlockPos adjacent = BlockPosHelper.getBlockAdjacent(pos, face);
                if (adjacent != null) {
                    TileEntity entity = worldObj.getTileEntity(adjacent);
                    if (entity != null && entity instanceof IEnergyReceiver) {
                        receiversMap.put(face, (IEnergyReceiver) entity);
                    }
                }
            }
        }

    }

    @Override
    public void onNeighborChange(BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(pos, neighbor);
        updateReceivers();
    }

    @Override
    public void onNeighborBlockChange(BlockPos pos, IBlockState state, Block neighborBlock) {
        super.onNeighborBlockChange(pos, state, neighborBlock);
        updateReceivers();
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


    public STATE getState() {
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
    public int getField(int id) {
        switch (id) {
            case 2:
                return actualRate;
            case 3:
                return state.ordinal();
            case 4:
                return lastEnergyOut;
            case 5:
                return remaining;
            case 6:
                return totalFuel;
        }
        return super.getField(id);
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 2:
                actualRate = value;
                return;
            case 3:
                state = STATE.values()[value];
                return;
            case 4:
                lastEnergyOut = value;
                return;
            case 5:
                remaining = value;
                return;
            case 6:
                totalFuel = value;
                return;
        }
        super.setField(id, value);
    }

    @Override
    public int getFieldCount() {
        return super.getFieldCount() + 5;
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
        buf.writeBoolean(isActive);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        isActive = buf.readBoolean();
    }


}

