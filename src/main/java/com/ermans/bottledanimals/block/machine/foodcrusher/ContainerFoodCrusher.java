package com.ermans.bottledanimals.block.machine.foodcrusher;

import com.ermans.bottledanimals.block.machine.ContainerMachine;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerFoodCrusher extends ContainerMachine {


    public ContainerFoodCrusher(InventoryPlayer invPlayer, TileFoodCrusher entity) {
        super(invPlayer, entity);
        addSlotToContainer(new SlotAcceptValid(entity, 0, 79, 25));
        addSlotToContainer(new SlotRemoveOnly(entity, 1, 125, 47));
    }
}
