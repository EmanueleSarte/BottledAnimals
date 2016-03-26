package com.ermans.bottledanimals.block.machine.breeder;

import com.ermans.bottledanimals.block.machine.ContainerTile;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerBreeder extends ContainerTile {


    private TileBreeder tileBreeder;

    public ContainerBreeder(InventoryPlayer invPlayer, TileBreeder entity) {
        super(invPlayer, entity);

        this.tileBreeder = entity;

        addSlotToContainer(new SlotAcceptValid(tileBreeder, 0, 48, 16));
        addSlotToContainer(new SlotAcceptValid(tileBreeder, 1, 48, 39));
        addSlotToContainer(new SlotAcceptValid(tileBreeder, 2, 76, 60));
        addSlotToContainer(new SlotAcceptValid(tileBreeder, 3, 100, 60));
        addSlotToContainer(new SlotRemoveOnly(tileBreeder, 4, 134, 28));
    }
}
