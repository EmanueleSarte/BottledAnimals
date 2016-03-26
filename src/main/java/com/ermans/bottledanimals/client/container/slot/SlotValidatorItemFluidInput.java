package com.ermans.bottledanimals.client.container.slot;


import com.ermans.repackage.cofh.lib.gui.slot.ISlotValidator;
import com.ermans.repackage.cofh.lib.util.helpers.FluidHelper;
import net.minecraft.item.ItemStack;

public class SlotValidatorItemFluidInput implements ISlotValidator{

    public static final SlotValidatorItemFluidInput INSTANCE = new SlotValidatorItemFluidInput();

    @Override
    public boolean isItemValid(ItemStack stack) {
        return FluidHelper.isFluidContainerItem(stack) || FluidHelper.isEmptyContainer(stack);
    }
}
