package com.ermans.bottledanimals.block.generator;


import com.ermans.bottledanimals.block.ContainerEnergy;
import com.ermans.bottledanimals.block.TileInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

public abstract class ContainerGenerator extends ContainerEnergy{

    private int actualRate;
    private int state;
    private int lastEnergyOut;
    private int remaining;
    private int totalFuel;

    public ContainerGenerator(InventoryPlayer player, TileInventory entity) {
        super(player, entity);
    }

    public ContainerGenerator(InventoryPlayer player, TileInventory entity, boolean setInvPlayer) {
        super(player, entity, setInvPlayer);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (ICrafting icrafting : this.crafters) {
            if (actualRate != baseTile.getField(1)){
                icrafting.sendProgressBarUpdate(this, 1, baseTile.getField(1));
                actualRate = baseTile.getField(1);
            }
            if (state != baseTile.getField(2)){
                icrafting.sendProgressBarUpdate(this, 2, baseTile.getField(2));
                state = baseTile.getField(2);
            }
            if (lastEnergyOut != baseTile.getField(3)){
                icrafting.sendProgressBarUpdate(this, 3, baseTile.getField(3));
                lastEnergyOut = baseTile.getField(3);
            }
            if (remaining != baseTile.getField(4)){
                icrafting.sendProgressBarUpdate(this, 4, baseTile.getField(4));
                remaining = baseTile.getField(4);
            }
            if (totalFuel != baseTile.getField(5)){
                icrafting.sendProgressBarUpdate(this, 5, baseTile.getField(5));
                totalFuel = baseTile.getField(5);
            }
        }
    }
}
