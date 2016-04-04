package com.ermans.bottledanimals.block.machine;

import com.ermans.api.IMachineInfo;
import com.ermans.bottledanimals.helper.TargetPointHelper;
import com.ermans.bottledanimals.network.PacketHandler;
import com.ermans.bottledanimals.network.message.MessageTile;
import com.ermans.bottledanimals.recipe.IRecipe;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileMachine extends TilePowered implements IMachineInfo {


    protected byte powerMult;
    //greater timeMult, lesser the operation total time
    protected byte timeMult;
    //remaining time
    protected int remaining;
    protected int operationTime;
    protected int recipeCode;

    protected boolean isActive;
    protected boolean processRecipes;


    @Override
    public void initTile() {
        super.initTile();
        this.powerMult = 1;
        this.timeMult = 1;
        this.processRecipes = true;
    }

    @Override
    public void update() {
        super.update();

        if (processRecipes) {
            if (worldObj.isRemote) {
                return;
            }

            boolean sendUpdate = false;
            //To track if the state changes
            boolean isChanged = isActive;
            int trackRecipeCode = recipeCode;

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
            }else{
                if (remaining > 0){
                    remaining = 0;
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

            if (isActive && checkTick(4) || sendUpdate || trackRecipeCode != recipeCode) {
                this.worldObj.addBlockEvent(pos, getBlockType(), 1, recipeCode);
                this.worldObj.addBlockEvent(pos, getBlockType(), 2, remaining);
            }

            //If there is a update, sync the machine
            if (sendUpdate) {
                syncMachine(isChanged != isActive);
                this.worldObj.addBlockEvent(pos, getBlockType(), 3, operationTime);
            }
        }
    }

    protected void syncMachine(boolean updateTexture) {

        PacketHandler.INSTANCE.sendToAllAround(new MessageTile(this, updateTexture), TargetPointHelper.getTargetPoint(worldObj, pos));
        markDirty();

        if (updateTexture) {
            worldObj.markBlockForUpdate(pos);
        }
    }


    //Machines can implement these if they need
    protected boolean updateMachine() {
        return false;
    }

    protected void operationStopped() {
    }

    protected void startProcess() {
    }

    //Machines need to implement these
    abstract protected boolean canStillProcess(int recipeCode);

    abstract protected IRecipe getRecipeIfCanStart();

    abstract protected void finishProcess();


    //Easy storage
    protected boolean enoughEnergyOperation() {
        return this.getEnergyStored(null) >= getTotalOperationEnergy();
    }

    protected int getTotalOperationEnergy() {
        return operationTime * RFTick;
    }

    protected int calculateEnergy() {
        return RFTick * powerMult;
    }


    public int getProgressScaled(int scale) {
        //dirty way to avoid progress bar from blinking
        if (!isActive || operationTime == 0 || remaining == 0) return 0;
        return scale * (operationTime - remaining) / operationTime;
    }


    //Check if someone right clicks an itemstack (to half it) inside the machine
    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack stack = super.decrStackSize(slot, amount);
        if (!worldObj.isRemote && remaining > 0 && isActive && shouldStopIfChangeSlot(slot, 0) && !canStillProcess(this.recipeCode)) {
            stopOperation();
        }
        return stack;
    }

    //Check if someone changes the itemstacks inside the machine
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
        recipeCode = -1;
        operationStopped();
        syncMachine(true);
    }

    //If someone changed a slot, should I worry?
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
        return storage.getMaxReceive();
    }

    @Override
    public int getInfoEnergyPerTick() {
        if (!isActive) return 0;
        return calculateEnergy();
    }
    @Override
    public int getInfoTimePercentage() {
        if (!isActive) return 0;
        return (operationTime - remaining) * 100 / operationTime;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }


    ////DATA SYNC
    @Override
    public boolean receiveClientEvent(int action, int value) {
        switch (action) {
            case 1:
                recipeCode = value;
                return true;
            case 2:
                remaining = value;
                return true;
            case 3:
                operationTime = value;
                return true;
            default:
                return super.receiveClientEvent(action, value);
        }
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
        this.isActive = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeBoolean(this.isActive);
    }
}


