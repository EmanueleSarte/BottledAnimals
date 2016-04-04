package com.ermans.bottledanimals.block.machine.foodcrusher;

import com.ermans.bottledanimals.block.ContainerTile;
import com.ermans.bottledanimals.client.container.slot.SlotValidatorItemFluidInput;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import com.ermans.repackage.cofh.lib.gui.slot.SlotValidated;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerFoodCrusher extends ContainerTile {


    public ContainerFoodCrusher(InventoryPlayer invPlayer, TileFoodCrusher entity) {
        super(invPlayer, entity);
        addSlotToContainer(new SlotAcceptValid(entity, 0, 61, 22));
        addSlotToContainer(new SlotValidated(SlotValidatorItemFluidInput.INSTANCE, entity, 1, 129, 17));
        addSlotToContainer(new SlotRemoveOnly(entity, 2, 96, 46));
        addSlotToContainer(new SlotRemoveOnly(entity, 3, 129, 46));
    }
}
