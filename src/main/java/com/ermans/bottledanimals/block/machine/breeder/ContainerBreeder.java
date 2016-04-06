package com.ermans.bottledanimals.block.machine.breeder;

import com.ermans.bottledanimals.block.machine.ContainerMachine;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerBreeder extends ContainerMachine {



    public ContainerBreeder(InventoryPlayer invPlayer, TileBreeder entity) {
        super(invPlayer, entity);

        addSlotToContainer(new SlotAcceptValid(entity, 0, 48, 16));
        addSlotToContainer(new SlotAcceptValid(entity, 1, 48, 39));
        addSlotToContainer(new SlotAcceptValid(entity, 2, 76, 60));
        addSlotToContainer(new SlotAcceptValid(entity, 3, 100, 60));
        addSlotToContainer(new SlotRemoveOnly(entity, 4, 134, 28));
    }
}
