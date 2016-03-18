package com.ermans.bottledanimals.block.machine;

import com.ermans.bottledanimals.block.IEnergyInfoReceiver;
import com.ermans.bottledanimals.helper.TargetPointHelper;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.network.message.MessageTile;
import com.ermans.bottledanimals.recipe.IRecipe;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileMachine extends TileInventory implements IEnergyInfoReceiver {


    protected byte powerMult;
    //greater timeMult, lesser the operation total time
    protected byte timeMult;
    //remaining time
    public int remaining;
    public int operationTime;
    public int recipeCode;

    public boolean isActive;
    public boolean updateEntity;

    public boolean needSyncText;


    @Override
    public void initTile() {
        super.initTile();
        this.powerMult = 1;
        this.timeMult = 1;
        this.updateEntity = true;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (updateEntity) {
            if (worldObj.isRemote) {
                return;
            }

            boolean sendUpdate = false;
            //To track if the state changes
            boolean isChanged = isActive;

            if (isActive) {
                //We don't need to check energy because the operation starts only if there is enough energy for the operation
                if (remaining > 0) {
                    //if i'm here the right stack are still here otherwise 'decrStackSize' or 'setInventorySlotContents' set remaining to 0
                    if (updateMachine()) {  //We call this method if someone wants to do some stuff when machine works
                        sendUpdate = true;
                    }
                    this.modifyEnergyStored(-1 * calculateEnergy());
                    remaining -= timeMult;
                }

                if (remaining <= 0 && canStillProcess(this.recipeCode)) {
                    finishProcess();
                    sendUpdate = true;
                    isActive = false;
                }
            }
            //If we can start cooking again, we just do it so we haven't to mess with blinking value/texture
            if (remaining <= 0 && hasPassedRedstoneTest()) {
                IRecipe recipe = getRecipeIfCanStart();
                if (recipe != null) {
                    operationTime = recipe.getRecipeTime();
                    if (enoughEnergyOperation()) {
                        startProcess();
                        remaining = operationTime;
                        recipeCode = recipe.getCode();
                        isActive = true;
                        sendUpdate = true;
                    }
                }
            }

            //If there is a update or each 4 tick, sync the machine
            if (sendUpdate || (isActive && checkTick(4))) {
                syncMachine(isChanged != isActive);
            }
        }
    }


    //if true updateTexture will call markBlockForRenderUpdate
    protected void syncMachine(boolean updateTexture) {
        //this is called clientside (I mean the message) to update the texture
        PacketHandler.INSTANCE.sendToAllAround(new MessageTile(this, updateTexture), TargetPointHelper.getTargetPoint(this));
        markDirty();
        //This is called server side to update light
        if (updateTexture) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }


    protected boolean updateMachine() {
        return false;
    }

    protected void operationStopped() {
    }

    protected void startProcess() {
    }

    abstract protected boolean canStillProcess(int recipeCode);

    abstract protected IRecipe getRecipeIfCanStart();

    abstract protected void finishProcess();


    private boolean enoughEnergyOperation() {
        return this.getEnergyStored(null) >= getTotalOperationEnergy();
    }

    protected int getTotalOperationEnergy() {
        return operationTime * RFTick;
    }

    private int calculateEnergy() {
        return RFTick * powerMult;
    }

    public int getProgressScaled(int scale) {
        if (!isActive) return 0;
        return scale * (operationTime - remaining) / operationTime;
    }

    @Override
    public int getSizeInventory() {
        return getNumInput() + getNumOutput();
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack stack = super.decrStackSize(slot, amount);
        if (!worldObj.isRemote && remaining > 0 && isActive && shouldStopIfChangeSlot(slot, 0) && !canStillProcess(this.recipeCode)) {
            stopOperation();
        }
        return stack;
    }


    @Override
    public void setInventorySlotContents(int slot, ItemStack contents) {
        super.setInventorySlotContents(slot, contents);
        if (!worldObj.isRemote && remaining > 0 && isActive && shouldStopIfChangeSlot(slot, 0) && !canStillProcess(this.recipeCode)) {
            stopOperation();
        }
    }

    protected final void stopOperation(){
        remaining = 0;
        isActive = false;
        operationStopped();
        syncMachine(true);
        recipeCode = -1;
    }

    protected boolean shouldStopIfChangeSlot(int slotIndex, int mode) {
        return invHelper.isInput(slotIndex);
    }


    //////////////IEnergyInfo///////////
    @Override
    public int getInfoMaxEnergyStored() {
        return storage.getMaxEnergyStored();
    }

    @Override
    public int getInfoEnergyStored() {
        return storage.getEnergyStored();
    }

    @Override
    public int getInfoMaxEnergyPerTick() {
        return getInfoEnergyPerTick();
    }

    @Override
    public int getInfoEnergyPerTick() {
        if (!isActive) return 0;
        return calculateEnergy();
    }

    @Override
    public int getInfoMaxReceiveEnergyPerTick() {
        return getMaxReceiveEnergy();
    }

    @Override
    public int getInfoTimePercentage() {
        if (!isActive) return 0;
        return (operationTime - remaining) * 100 / operationTime;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("active", isActive);
        nbtTagCompound.setInteger("remaining", remaining);
        nbtTagCompound.setInteger("operationTime", operationTime);
        nbtTagCompound.setInteger("recipeCode", recipeCode);

    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        isActive = nbtTagCompound.getBoolean("active");
        remaining = nbtTagCompound.getInteger("remaining");
        operationTime = nbtTagCompound.getInteger("operationTime");
        recipeCode = nbtTagCompound.getInteger("recipeCode");
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.remaining = buf.readInt();
        this.isActive = buf.readBoolean();
        this.operationTime = buf.readInt();
        this.recipeCode = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeInt(this.remaining);
        buf.writeBoolean(this.isActive);
        buf.writeInt(this.operationTime);
        buf.writeInt(this.recipeCode);
    }
}


