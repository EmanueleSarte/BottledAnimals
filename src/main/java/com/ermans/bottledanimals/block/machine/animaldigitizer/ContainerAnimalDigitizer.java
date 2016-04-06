package com.ermans.bottledanimals.block.machine.animaldigitizer;

import com.ermans.bottledanimals.block.machine.ContainerMachine;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerAnimalDigitizer extends ContainerMachine {


    public ContainerAnimalDigitizer(InventoryPlayer invPlayer, TileAnimalDigitizer entity) {
        super(invPlayer, entity);

        addSlotToContainer(new SlotAcceptValid(entity, 0, 53, 31));
        addSlotToContainer(new SlotAcceptValid(entity, 1, 53, 53));
        addSlotToContainer(new SlotRemoveOnly(entity, 2, 116, 31));
    }


}
