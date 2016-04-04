package com.ermans.bottledanimals.block.machine.growthaccelerator;

import com.ermans.bottledanimals.block.ContainerTile;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerGrowthAccelerator extends ContainerTile {

    private TileGrowthAccelerator tileGrowthAccelerator;

    public ContainerGrowthAccelerator(InventoryPlayer invPlayer, TileGrowthAccelerator entity) {
        super(invPlayer, entity);
        this.tileGrowthAccelerator = entity;
        addSlotToContainer(new SlotAcceptValid(tileGrowthAccelerator, 0, 45, 38));
        addSlotToContainer(new SlotAcceptValid(tileGrowthAccelerator, 1, 45, 16));
        addSlotToContainer(new SlotRemoveOnly(tileGrowthAccelerator, 2, 116, 38));
    }
}
