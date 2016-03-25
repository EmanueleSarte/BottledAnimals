package com.ermans.bottledanimals.block.machine.rancher;

import com.ermans.bottledanimals.block.machine.ContainerTile;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerRancher extends ContainerTile {

    public ContainerRancher(InventoryPlayer invPlayer, TileRancher entity) {
        super(invPlayer, entity);
        addSlotToContainer(new SlotAcceptValid(entity, 0, 79, 19));
        addSlotToContainer(new SlotAcceptValid(entity, 1, 44, 45));
        addSlotToContainer(new SlotRemoveOnly(entity, 2, 114, 45));

    }
}
