package com.ermans.bottledanimals.block;


import com.ermans.repackage.cofh.lib.gui.container.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public abstract class ContainerTile extends ContainerBase {

    public TileInventory baseTile;
    protected boolean hasPlayerInvSlots = true;


    public ContainerTile(InventoryPlayer player, TileInventory entity) {
        this(player, entity, true);
    }

    public ContainerTile(InventoryPlayer player, TileInventory entity, boolean setInvPlayer) {
        this.baseTile = entity;

        this.hasPlayerInvSlots = setInvPlayer;
        if (this.hasPlayerInvSlots) {
            bindPlayerInventory(player);
        }
    }

    @Override
    protected boolean performMerge(int paramInt, ItemStack paramItemStack) {
        int i = 0;
        int j = i + 27;
        int k = j + 9;
        int m = k + (this.baseTile == null ? 0 : this.baseTile.invHelper.getTotalSlotCount());
        if (paramInt < k) {
            return mergeItemStack(paramItemStack, k, m, false);
        }
        return mergeItemStack(paramItemStack, i, k, true);
    }

    @Override
    protected int getPlayerInventoryVerticalOffset() {
        return 84;
    }

    @Override
    protected int getSizeInventory() {
        return this.baseTile.getSizeInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.baseTile.isUseableByPlayer(player);
    }



}
