package com.ermans.bottledanimals.block.machine.animalmaterializer;


import com.ermans.bottledanimals.block.ContainerTile;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import com.ermans.repackage.cofh.lib.gui.slot.SlotRemoveOnly;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerAnimalMaterializer extends ContainerTile{


    private TileAnimalMaterializer tileAnimalMaterializer;

    public ContainerAnimalMaterializer(InventoryPlayer invPlayer, TileAnimalMaterializer entity) {
        super(invPlayer, entity);

        this.tileAnimalMaterializer = entity;

        addSlotToContainer(new SlotAcceptValid(tileAnimalMaterializer, 0, 46, 36));
        addSlotToContainer(new SlotAcceptValid(tileAnimalMaterializer, 1, 68, 36));
        addSlotToContainer(new SlotRemoveOnly(tileAnimalMaterializer, 2, 134, 36));
    }

}
