package com.ermans.bottledanimals.block.machine;


import com.ermans.bottledanimals.block.ContainerEnergy;
import com.ermans.bottledanimals.block.TileInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

public abstract class ContainerMachine extends ContainerEnergy {

    private int recipeCode;
    private int remaining;
    private int operationTime;


    public ContainerMachine(InventoryPlayer player, TileInventory entity) {
        super(player, entity);

    }

    public ContainerMachine(InventoryPlayer player, TileInventory entity, boolean setInvPlayer) {
        super(player, entity, setInvPlayer);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (ICrafting icrafting : this.crafters) {
            if (recipeCode != baseTile.getField(3)) {
                icrafting.sendProgressBarUpdate(this, 3, baseTile.getField(3));
                recipeCode = baseTile.getField(3);
            }
            if (remaining != baseTile.getField(4)) {
                icrafting.sendProgressBarUpdate(this, 4, baseTile.getField(4));
                remaining = baseTile.getField(4);
            }
            if (operationTime != baseTile.getField(5)) {
                icrafting.sendProgressBarUpdate(this, 5, baseTile.getField(5));
                operationTime = baseTile.getField(5);
            }
        }
    }
}
