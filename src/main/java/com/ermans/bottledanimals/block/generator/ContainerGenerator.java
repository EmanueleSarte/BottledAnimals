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
            if (actualRate != baseTile.getField(2)){
                icrafting.sendProgressBarUpdate(this, 2, baseTile.getField(2));
                actualRate = baseTile.getField(2);
            }
            if (state != baseTile.getField(3)){
                icrafting.sendProgressBarUpdate(this, 3, baseTile.getField(3));
                state = baseTile.getField(3);
            }
            if (lastEnergyOut != baseTile.getField(4)){
                icrafting.sendProgressBarUpdate(this, 4, baseTile.getField(4));
                lastEnergyOut = baseTile.getField(4);
            }
            if (remaining != baseTile.getField(5)){
                icrafting.sendProgressBarUpdate(this, 5, baseTile.getField(5));
                remaining = baseTile.getField(5);
            }
            if (totalFuel != baseTile.getField(6)){
                icrafting.sendProgressBarUpdate(this, 6, baseTile.getField(6));
                totalFuel = baseTile.getField(6);
            }
        }
    }
}
