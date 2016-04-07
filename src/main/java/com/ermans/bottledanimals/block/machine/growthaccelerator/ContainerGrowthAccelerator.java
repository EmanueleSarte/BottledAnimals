package com.ermans.bottledanimals.block.machine.growthaccelerator;

import com.ermans.bottledanimals.block.machine.ContainerMachine;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

public class ContainerGrowthAccelerator extends ContainerMachine {

    private int multLevel;

    public ContainerGrowthAccelerator(InventoryPlayer invPlayer, TileGrowthAccelerator entity) {
        super(invPlayer, entity);
        addSlotToContainer(new SlotAcceptValid(entity, 0, 45, 38));
        addSlotToContainer(new SlotAcceptValid(entity, 1, 45, 16));
        addSlotToContainer(new SlotRemoveOnly(entity, 2, 116, 38));
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (ICrafting icrafting : this.crafters) {
            if (multLevel != baseTile.getField(5)) {
                icrafting.sendProgressBarUpdate(this, 5, baseTile.getField(5));
                multLevel = baseTile.getField(5);
            }
        }
    }
}
