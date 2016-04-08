package com.ermans.bottledanimals.block;


import cofh.api.energy.IEnergyReceiver;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

public abstract class ContainerEnergy extends ContainerTile {

    private int energyStored;
    private int lastEnergyIn;
    private boolean isReceiver;

    public ContainerEnergy(InventoryPlayer player, TileInventory entity) {
        this(player, entity, true);
    }

    public ContainerEnergy(InventoryPlayer player, TileInventory entity, boolean setInvPlayer) {
        super(player, entity, setInvPlayer);
        isReceiver = entity instanceof IEnergyReceiver;
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (ICrafting icrafting : this.crafters) {
            if (this.energyStored != baseTile.getField(1)){
                icrafting.sendProgressBarUpdate(this, 1, baseTile.getField(1));
                energyStored = baseTile.getField(1);
            }
            if (isReceiver){
                if (this.lastEnergyIn != baseTile.getField(2)){
                    icrafting.sendProgressBarUpdate(this, 2, baseTile.getField(2));
                    lastEnergyIn = baseTile.getField(2);
                }
            }

        }
    }
}
