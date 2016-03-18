package com.ermans.repackage.cofh.lib.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Slot that will only accept ItemStacks when the IInventory returns true from isItemValidForSlot.
 */
public class SlotAcceptValid extends Slot {

    public static final int VANILLA_SLOT = 36;
    protected int slotCountOffset;

    public SlotAcceptValid(IInventory inventory, int index, int x, int y) {
        this(inventory, index, x, y, VANILLA_SLOT);
    }

    public SlotAcceptValid(IInventory inventory, int index, int x, int y, int slotCountOffset) {
        super(inventory, index, x, y);
        this.slotCountOffset = slotCountOffset;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {

        return stack != null && this.inventory.isItemValidForSlot(this.slotNumber - slotCountOffset, stack);
    }

}
