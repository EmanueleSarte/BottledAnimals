package com.ermans.bottledanimals.block;


import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

public abstract class ContainerEnergy extends ContainerTile {

    private int energyStored;

    public ContainerEnergy(InventoryPlayer player, TileInventory entity) {
        super(player, entity);
    }

    public ContainerEnergy(InventoryPlayer player, TileInventory entity, boolean setInvPlayer) {
        super(player, entity, setInvPlayer);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (ICrafting icrafting : this.crafters) {
            if (this.energyStored != baseTile.getField(100)){
                icrafting.sendProgressBarUpdate(this, 100, baseTile.getField(100));
                energyStored = baseTile.getField(100);
            }
        }
    }
}
