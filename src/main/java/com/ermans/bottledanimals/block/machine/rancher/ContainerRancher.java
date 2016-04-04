package com.ermans.bottledanimals.block.machine.rancher;

import com.ermans.bottledanimals.block.ContainerTile;
import com.ermans.bottledanimals.client.container.slot.SlotValidatorItemFluidInput;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import com.ermans.repackage.cofh.lib.gui.slot.SlotValidated;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerRancher extends ContainerTile {

    public ContainerRancher(InventoryPlayer invPlayer, TileRancher entity) {
        super(invPlayer, entity);
        addSlotToContainer(new SlotAcceptValid(entity, 0, 68, 19));
        addSlotToContainer(new SlotAcceptValid(entity, 1, 44, 45));
        addSlotToContainer(new SlotValidated(SlotValidatorItemFluidInput.INSTANCE, entity, 2, 129, 17));
        addSlotToContainer(new SlotRemoveOnly(entity, 3, 97, 45));
        addSlotToContainer(new SlotValidated(SlotValidatorItemFluidInput.INSTANCE, entity, 4, 129, 46));

    }
}

