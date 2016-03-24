package com.ermans.bottledanimals.block.machine.wirelessfeeder;

import com.ermans.bottledanimals.block.machine.ContainerTile;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerWirelessFeeder extends ContainerTile {

    public ContainerWirelessFeeder(InventoryPlayer invPlayer, TileWirelessFeeder entity) {
        super(invPlayer, entity);
        addSlotToContainer(new SlotAcceptValid(entity, 0, 129, 13));
        addSlotToContainer(new SlotRemoveOnly(entity, 1, 129, 49));
    }
}

