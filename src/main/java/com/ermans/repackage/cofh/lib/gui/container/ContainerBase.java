package com.ermans.repackage.cofh.lib.gui.container;

import com.ermans.repackage.cofh.lib.util.helpers.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class ContainerBase extends Container {

    public ContainerBase() {

    }

    protected abstract int getPlayerInventoryVerticalOffset();

    protected int getPlayerInventoryHorizontalOffset() {
        return 8;
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {

        int yOff = getPlayerInventoryVerticalOffset();
        int xOff = getPlayerInventoryHorizontalOffset();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, xOff + j * 18, yOff + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, xOff + i * 18, yOff + 58));
        }
    }

    protected abstract int getSizeInventory();

    protected boolean supportsShiftClick(EntityPlayer player, int slotIndex) {
        return supportsShiftClick(slotIndex);
    }

    protected boolean supportsShiftClick(int slotIndex) {
        return true;
    }

    protected boolean performMerge(EntityPlayer player, int slotIndex, ItemStack stack) {
        return performMerge(slotIndex, stack);
    }

    protected boolean performMerge(int slotIndex, ItemStack stack) {

        int invBase = getSizeInventory();
        int invFull = inventorySlots.size();

        if (slotIndex < invBase) {
            return mergeItemStack(stack, invBase, invFull, true);
        }
        return mergeItemStack(stack, 0, invBase, false);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {

        if (!supportsShiftClick(player, slotIndex)) {
            return null;
        }

        ItemStack stack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();

            if (!performMerge(player, slotIndex, stackInSlot)) {
                return null;
            }

            if (stackInSlot.stackSize <= 0) {
                slot.putStack(null);
            } else {
                slot.putStack(stackInSlot);
            }

            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }

//    @SideOnly(Side.CLIENT)
//    @Override
//    public void putStacksInSlots(ItemStack[] stacks) {
//        for (int i = 0; i < stacks.length; ++i) {
//            putStackInSlot(i, stacks[i]);
//        }
//    }


    @SuppressWarnings("unchecked")
    @Override
    protected boolean mergeItemStack(ItemStack stack, int slotMin, int slotMax, boolean ascending) {

        return mergeItemStack(this.inventorySlots, stack, slotMin, slotMax, ascending);
    }

    public static boolean mergeItemStack(List<Slot> slots, ItemStack stack, int start, int length, boolean reverse) {

        return mergeItemStack(slots, stack, start, length, reverse, true);
    }

    public static boolean mergeItemStack(List<Slot> slots, ItemStack stack, int start, int length, boolean r, boolean limit) {

        boolean successful = false;
        int i = !r ? start : length - 1;
        int iterOrder = !r ? 1 : -1;

        Slot slot;
        ItemStack existingStack;

        if (stack.isStackable()) {
            while (stack.stackSize > 0 && (!r && i < length || r && i >= start)) {
                slot = slots.get(i);
                existingStack = slot.getStack();

                if (existingStack != null) {
                    int maxStack = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());
                    int rmv = Math.min(maxStack, stack.stackSize);

                    if (slot.isItemValid(ItemHelper.cloneStack(stack, rmv)) && existingStack.getItem().equals(stack.getItem())
                            && (!stack.getHasSubtypes() || stack.getItemDamage() == existingStack.getItemDamage())
                            && ItemStack.areItemStackTagsEqual(stack, existingStack)) {
                        int existingSize = existingStack.stackSize + stack.stackSize;

                        if (existingSize <= maxStack) {
                            stack.stackSize = 0;
                            existingStack.stackSize = existingSize;
                            slot.putStack(existingStack);
                            successful = true;
                        } else if (existingStack.stackSize < maxStack) {
                            stack.stackSize -= maxStack - existingStack.stackSize;
                            existingStack.stackSize = maxStack;
                            slot.putStack(existingStack);
                            successful = true;
                        }
                    }
                }

                i += iterOrder;
            }
        }

        if (stack.stackSize > 0) {
            i = !r ? start : length - 1;

            while (stack.stackSize > 0 && (!r && i < length || r && i >= start)) {
                slot = slots.get(i);
                existingStack = slot.getStack();

                if (existingStack == null) {
                    int maxStack = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());
                    int rmv = Math.min(maxStack, stack.stackSize);

                    if (slot.isItemValid(ItemHelper.cloneStack(stack, rmv))) {
                        existingStack = stack.splitStack(rmv);
                        slot.putStack(existingStack);
                        successful = true;
                    }
                }

                i += iterOrder;
            }
        }

        return successful;
    }

}
