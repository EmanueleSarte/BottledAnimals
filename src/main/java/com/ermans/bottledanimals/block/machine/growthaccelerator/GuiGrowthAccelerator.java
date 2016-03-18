package com.ermans.bottledanimals.block.machine.growthaccelerator;

import com.ermans.bottledanimals.client.gui.GuiBaseAdv;
import com.ermans.bottledanimals.client.gui.tab.TabInfo;
import com.ermans.bottledanimals.reference.Textures;
import com.ermans.repackage.cofh.lib.gui.element.ElementDualScaled;
import com.ermans.repackage.cofh.lib.gui.element.ElementEnergyStored;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import net.minecraft.entity.player.InventoryPlayer;

import java.util.Arrays;

public class GuiGrowthAccelerator extends GuiBaseAdv {

    private TileGrowthAccelerator tile;
    private ElementDualScaled speed;
    private ElementDualScaled mult;

    public GuiGrowthAccelerator(InventoryPlayer invPlayer, TileGrowthAccelerator entity) {
        super(new ContainerGrowthAccelerator(invPlayer, entity), Textures.Gui.GROWTH_ACCELERATOR, entity);
        this.tile = entity;
    }

    @Override
    public void initGui() {
        super.initGui();
        addTab(new TabInfo(this, TabBase.LEFT, "Turn a baby animals into full sized animals"));

        addElement(new ElementEnergyStored(this, tile.getEnergyStorage()));

        this.speed = ((ElementDualScaled) addElement(new ElementDualScaled(this, 73, 38).setMode(1).setSize(24, 16).setTexture(Textures.Gui.Element.PROGRESS_ARROW, 64, 16)));
        this.mult = ((ElementDualScaled) addElement(new ElementDualScaled(this, 45, 59).setMode(1).setSize(15, 10).setTexture(Textures.Gui.Element.PROGRESS_MULT, 32, 16).setTooltipText(Arrays.asList("Speed"))));


    }



    @Override
    protected void updateElementInformation() {
        super.updateElementInformation();
        this.speed.setQuantity(this.tile.getProgressScaled(24));
        this.mult.setQuantity(this.tile.getMultiplierLevelScaled(15));
    }
}
