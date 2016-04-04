package com.ermans.bottledanimals.block.machine.animaldigitizer;

import com.ermans.bottledanimals.block.ContainerTile;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerAnimalDigitizer extends ContainerTile {

    private TileAnimalDigitizer tileAnimalDigitizer;

    public ContainerAnimalDigitizer(InventoryPlayer invPlayer, TileAnimalDigitizer entity) {
        super(invPlayer, entity);

        this.tileAnimalDigitizer = entity;

        addSlotToContainer(new SlotAcceptValid(tileAnimalDigitizer, 0, 53, 31));
        addSlotToContainer(new SlotAcceptValid(tileAnimalDigitizer, 1, 53, 53));
        addSlotToContainer(new SlotRemoveOnly(tileAnimalDigitizer, 2, 116, 31));
    }


}
