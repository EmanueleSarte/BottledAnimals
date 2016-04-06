package com.ermans.bottledanimals.block.machine.dropextractor;

import com.ermans.bottledanimals.block.machine.ContainerMachine;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerDropExtractor extends ContainerMachine {

    public ContainerDropExtractor(InventoryPlayer invPlayer, TileDropExtractor entity) {
        super(invPlayer, entity);
        addSlotToContainer(new SlotAcceptValid(entity, 0, 53, 29));
        addSlotToContainer(new SlotRemoveOnly(entity, 1, 116, 29));
        addSlotToContainer(new SlotRemoveOnly(entity, 2, 140, 33));
        addSlotToContainer(new SlotRemoveOnly(entity, 3, 116, 56));
    }
}
