package com.ermans.bottledanimals.block.machine.wirelessfeeder;

import com.ermans.bottledanimals.block.machine.ContainerTile;
import com.ermans.bottledanimals.client.container.slot.SlotValidatorItemFluidOutput;
import com.ermans.repackage.cofh.lib.gui.slot.SlotValidated;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerWirelessFeeder extends ContainerTile {

    public ContainerWirelessFeeder(InventoryPlayer invPlayer, TileWirelessFeeder entity) {
        super(invPlayer, entity);

        addSlotToContainer(new SlotValidated(SlotValidatorItemFluidOutput.INSTANCE, entity, 0, 130, 12));
    }
}

