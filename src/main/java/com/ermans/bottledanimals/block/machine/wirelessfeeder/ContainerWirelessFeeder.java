package com.ermans.bottledanimals.block.machine.wirelessfeeder;

import com.ermans.bottledanimals.block.machine.ContainerMachine;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerWirelessFeeder extends ContainerMachine {

    public ContainerWirelessFeeder(InventoryPlayer invPlayer, TileWirelessFeeder entity) {
        super(invPlayer, entity);
    }
}
