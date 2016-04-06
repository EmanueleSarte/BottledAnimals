package com.ermans.bottledanimals.block.machine.foodcrusher;

import com.ermans.bottledanimals.client.gui.GuiBaseAdv;
import com.ermans.bottledanimals.client.gui.tab.TabInfo;
import com.ermans.bottledanimals.reference.Textures;
import com.ermans.repackage.cofh.lib.gui.element.ElementDualScaled;
import com.ermans.repackage.cofh.lib.gui.element.ElementEnergyStored;
import com.ermans.repackage.cofh.lib.gui.element.ElementFluidTank;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiFoodCrusher extends GuiBaseAdv {

    private TileFoodCrusher tile;
    private ElementDualScaled speed;

    public GuiFoodCrusher(InventoryPlayer invPlayer, TileFoodCrusher entity) {
        super(new ContainerFoodCrusher(invPlayer, entity), Textures.Gui.FOOD_CRUSHER, entity);
        this.tile = entity;
    }

    @Override
    public void initGui() {
        super.initGui();
        addTab(new TabInfo(this, TabBase.LEFT, "Breaks up the food until it becomes liquid"));

        addElement(new ElementEnergyStored(this, tile.getEnergyStorage()).setAlwaysShow(true));
        addElement(new ElementFluidTank(this, tile.getFluidTank()).setGauge(1).setAlwaysShow(true));

        this.speed = ((ElementDualScaled) addElement(new ElementDualScaled(this, 61, 41).setMode(0).setSize(16, 16).setTexture(Textures.Gui.Element.PROGRESS_SAW, 32, 16)));
    }

    @Override
    protected void updateElementInformation() {
        super.updateElementInformation();
        this.speed.setQuantity(this.tile.getProgressScaled(16));
    }

}
