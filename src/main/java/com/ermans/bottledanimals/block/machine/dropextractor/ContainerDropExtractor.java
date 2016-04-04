package com.ermans.bottledanimals.block.machine.dropextractor;

import com.ermans.bottledanimals.block.ContainerTile;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerDropExtractor extends ContainerTile {
    private TileDropExtractor tileDropExtractor;

    public ContainerDropExtractor(InventoryPlayer invPlayer, TileDropExtractor entity) {
        super(invPlayer, entity);
        this.tileDropExtractor = entity;
        addSlotToContainer(new SlotAcceptValid(tileDropExtractor, 0, 53, 29));
        addSlotToContainer(new SlotRemoveOnly(tileDropExtractor, 1, 116, 29));
        addSlotToContainer(new SlotRemoveOnly(tileDropExtractor, 2, 140, 33));
        addSlotToContainer(new SlotRemoveOnly(tileDropExtractor, 3, 116, 56));
    }
}
