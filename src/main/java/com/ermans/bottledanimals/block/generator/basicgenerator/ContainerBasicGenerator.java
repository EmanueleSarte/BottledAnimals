package com.ermans.bottledanimals.block.generator.basicgenerator;

import com.ermans.bottledanimals.block.generator.ContainerGenerator;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerBasicGenerator extends ContainerGenerator {

    private TileBasicGenerator tileBasicGenerator;

    public ContainerBasicGenerator(InventoryPlayer invPlayer, TileBasicGenerator entity) {
        super(invPlayer, entity);

        this.tileBasicGenerator = entity;
        addSlotToContainer(new SlotAcceptValid(tileBasicGenerator, 0, 77, 27));
    }

}
