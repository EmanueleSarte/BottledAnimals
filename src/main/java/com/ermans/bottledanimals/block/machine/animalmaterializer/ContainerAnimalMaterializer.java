package com.ermans.bottledanimals.block.machine.animalmaterializer;


import com.ermans.bottledanimals.block.machine.ContainerMachine;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerAnimalMaterializer extends ContainerMachine{


    public ContainerAnimalMaterializer(InventoryPlayer invPlayer, TileAnimalMaterializer entity) {
        super(invPlayer, entity);

        addSlotToContainer(new SlotAcceptValid(entity, 0, 46, 36));
        addSlotToContainer(new SlotAcceptValid(entity, 1, 68, 36));
        addSlotToContainer(new SlotRemoveOnly(entity, 2, 134, 36));
    }

}
