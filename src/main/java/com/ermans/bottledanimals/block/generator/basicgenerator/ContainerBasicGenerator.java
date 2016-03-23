package com.ermans.bottledanimals.block.generator.basicgenerator;

import com.ermans.bottledanimals.block.machine.ContainerMachine;
import com.ermans.repackage.cofh.lib.gui.slot.SlotAcceptValid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

public class ContainerBasicGenerator extends ContainerMachine{

    private TileBasicGenerator tileBasicGenerator;

    public ContainerBasicGenerator(InventoryPlayer invPlayer, TileBasicGenerator entity) {
        super(invPlayer, entity);

        this.tileBasicGenerator = entity;
        addSlotToContainer(new SlotAcceptValid(tileBasicGenerator, 0, 77, 27));
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int p_updateProgressBar_1_, int p_updateProgressBar_2_) {
        super.updateProgressBar(p_updateProgressBar_1_, p_updateProgressBar_2_);
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting) {
        super.addCraftingToCrafters(crafting);
        tileBasicGenerator.addCraftingToCrafters(this, crafting);
    }

    @Override
    public void detectAndSendChanges() {
        int a = 3;
//        for(int var1 = 0; var1 < this.crafters.size(); ++var1) {
//            ICrafting var2 = (ICrafting)this.crafters.get(var1);
//            if(this.lastCookTime != this.tileFurnace.furnaceCookTime) {
//                var2.sendProgressBarUpdate(this, 0, this.tileFurnace.furnaceCookTime);
//            }
//
//            if(this.lastBurnTime != this.tileFurnace.furnaceBurnTime) {
//                var2.sendProgressBarUpdate(this, 1, this.tileFurnace.furnaceBurnTime);
//            }
//
//            if(this.lastItemBurnTime != this.tileFurnace.currentItemBurnTime) {
//                var2.sendProgressBarUpdate(this, 2, this.tileFurnace.currentItemBurnTime);
//            }
//        }
//
//        this.lastCookTime = this.tileFurnace.furnaceCookTime;
//        this.lastBurnTime = this.tileFurnace.furnaceBurnTime;
//        this.lastItemBurnTime = this.tileFurnace.currentItemBurnTime;
    }

}
