package com.ermans.bottledanimals.block.machine.dropextractor;

import com.ermans.bottledanimals.block.machine.ContainerMachine;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerDropExtractor extends ContainerMachine {

    public ContainerDropExtractor(InventoryPlayer invPlayer, TileDropExtractor entity) {
        super(invPlayer, entity);
        addSlotToContainer(new SlotAcceptValid(entity, 0, 45, 29));
        addSlotToContainer(new SlotRemoveOnly(entity, 1, 106, 22));
        addSlotToContainer(new SlotRemoveOnly(entity, 2, 126, 22));
        addSlotToContainer(new SlotRemoveOnly(entity, 3, 106, 42));
        addSlotToContainer(new SlotRemoveOnly(entity, 4, 129, 45));
        addSlotToContainer(new SlotRemoveOnly(entity, 5, 152, 61));
    }
}
