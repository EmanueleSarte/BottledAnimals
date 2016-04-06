package com.ermans.bottledanimals.block.machine;


import com.ermans.bottledanimals.block.TileInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

public class ContainerFluid extends ContainerMachine {

    private int fluidAmount;

    public ContainerFluid(InventoryPlayer player, TileInventory entity) {
        super(player, entity);
    }

    public ContainerFluid(InventoryPlayer player, TileInventory entity, boolean setInvPlayer) {
        super(player, entity, setInvPlayer);
    }


    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (ICrafting icrafting : this.crafters) {
            if (fluidAmount != baseTile.getField(110)) {
                icrafting.sendProgressBarUpdate(this, 110, baseTile.getField(110));
                fluidAmount = baseTile.getField(110);
            }
        }
    }
}
