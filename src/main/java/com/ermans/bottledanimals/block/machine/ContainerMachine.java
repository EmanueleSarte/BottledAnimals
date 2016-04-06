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
            if (recipeCode != baseTile.getField(1)) {
                icrafting.sendProgressBarUpdate(this, 1, baseTile.getField(1));
                recipeCode = baseTile.getField(1);
            }
            if (remaining != baseTile.getField(2)) {
                icrafting.sendProgressBarUpdate(this, 2, baseTile.getField(2));
                remaining = baseTile.getField(2);
            }
            if (operationTime != baseTile.getField(3)) {
                icrafting.sendProgressBarUpdate(this, 3, baseTile.getField(3));
                operationTime = baseTile.getField(3);
            }
        }
    }
}
