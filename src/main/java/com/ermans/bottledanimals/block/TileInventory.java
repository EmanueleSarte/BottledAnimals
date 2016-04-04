package com.ermans.bottledanimals.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;

public abstract class TileInventory extends TileReconfigurable implements ISidedInventory {

    protected ItemStack[] inventory;
    protected int[] allSlot;
    protected InventoryHelper invHelper;


    @Override
    public void initTile() {
        super.initTile();
        this.invHelper = new InventoryHelper(getNumInput(), getNumOutput());
        this.inventory = new ItemStack[getNumInput() + getNumOutput()];

        this.allSlot = new int[this.inventory.length];
        for (int i = 0; i < allSlot.length; i++) {
            allSlot[i] = i;
        }
    }

    abstract protected int getNumInput();

    abstract protected int getNumOutput();


    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        if (canPlayerAccess(player)) {
            if (this.worldObj.getTileEntity(getPos()) != this) {
                return false;
            }
            return player.getDistanceSq(getPos().getX() + 0.5D, getPos().getY() + 0.5D, getPos().getZ() + 0.5D) <= 64.0D;
        }
        return false;
    }


    @Override
    public String getName() {
        return tileName;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(tileName);
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.inventory.length; ++i)        {
            this.inventory[i] = null;
        }
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (side == this.facing)
            return new int[]{};
        return allSlot;
    }


    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot < 0 || slot >= this.inventory.length) {
            return null;
        }
        return this.inventory[slot];
    }

    @Override
    public ItemStack removeStackFromSlot(int slotIndex) {
        ItemStack itemStack = inventory[slotIndex];
        this.inventory[slotIndex] = null;
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack contents) {
        this.inventory[slot] = contents;
    }


    @Override
    public int getInventoryStackLimit() {
        return 64;
    }


    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }


    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, EnumFacing direction) {
        return direction != facing && invHelper.isInput(slot) && this.isItemValidForSlot(slot, itemStack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing direction) {
        return direction != facing && invHelper.isOutput(slot);
    }


    @Override
    public int getSizeInventory() {
        return getNumInput() + getNumOutput();
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack fromStack = this.inventory[slot];
        if (fromStack == null) {
            return null;
        }
        if (fromStack.stackSize <= amount) {
            this.inventory[slot] = null;
            return fromStack;
        }
        ItemStack result = fromStack.copy();
        result.stackSize = amount;
        fromStack.stackSize -= amount;
        return result;
    }



    public void increaseStackSize(int slot, ItemStack stack) {
        ItemStack fromStack = this.inventory[slot];
        if (fromStack == null) {
            inventory[slot] = stack.copy();
            return;
        }

        if (!fromStack.isItemEqual(stack)){
            return;
        }

        int maxAllowed = fromStack.getMaxStackSize() - fromStack.stackSize;
        if (stack.stackSize > maxAllowed) {
            this.inventory[slot].stackSize = fromStack.getMaxStackSize();
            return;
        }
        inventory[slot].stackSize += stack.stackSize;
    }


    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);

        this.inventory = new ItemStack[this.inventory.length];
        NBTTagList itemList = (NBTTagList) nbtTagCompound.getTag("Items");
        if (itemList != null) {
            for (int i = 0; i < itemList.tagCount(); i++) {
                NBTTagCompound itemStack = itemList.getCompoundTagAt(i);
                byte slot = itemStack.getByte("Slot");
                if ((slot >= 0) && (slot < this.inventory.length)) {
                    this.inventory[slot] = ItemStack.loadItemStackFromNBT(itemStack);
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < this.inventory.length; i++) {
            if (this.inventory[i] != null) {
                NBTTagCompound itemStackNBT = new NBTTagCompound();
                itemStackNBT.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(itemStackNBT);
                itemList.appendTag(itemStackNBT);
            }
            nbtTagCompound.setTag("Items", itemList);
        }
    }


    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }


    //Don't mess with inventory or Itemstack here
    public class InventoryHelper {
        public int inputCount;
        public int outputCount;

        public InventoryHelper(int inputCount, int outputCount) {
            this.inputCount = inputCount;
            this.outputCount = outputCount;
        }

        public boolean isInput(int slot) {
            return slot < inputCount;
        }

        public boolean isOutput(int slot) {
            return slot >= inputCount && slot < inputCount + outputCount;
        }

        public int getInputCount() {
            return inputCount;
        }

        public int getOutputCount() {
            return outputCount;
        }

        public int getTotalSlotCount() {
            return inputCount + outputCount;
        }
    }
}
