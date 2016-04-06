package com.ermans.bottledanimals.block.machine.dropextractor;

import com.ermans.bottledanimals.client.gui.GuiBaseAdv;
import com.ermans.bottledanimals.client.gui.tab.TabInfo;
import com.ermans.bottledanimals.reference.Textures;
import com.ermans.repackage.cofh.lib.gui.element.ElementDualScaled;
import com.ermans.repackage.cofh.lib.gui.element.ElementEnergyStored;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiDropExtractor extends GuiBaseAdv {
    private TileDropExtractor tile;
    private ElementDualScaled speed;

    public GuiDropExtractor(InventoryPlayer invPlayer, TileDropExtractor entity) {
        super(new ContainerDropExtractor(invPlayer, entity), Textures.Gui.DROP_EXTRACTOR, entity);
        this.tile = entity;
    }

    @Override
    public void initGui() {
        super.initGui();
        addTab(new TabInfo(this, TabBase.LEFT, "Get Drop from a Digitalized Animal"));

        addElement(new ElementEnergyStored(this, tile.getEnergyStorage()).setAlwaysShow(true));

        this.speed = ((ElementDualScaled) addElement(new ElementDualScaled(this, 77, 29).setMode(1).setSize(26, 16).setTexture(Textures.Gui.Element.PROGRESS_EXTRACT, 64, 16)));
    }

    @Override
    protected void updateElementInformation() {
        super.updateElementInformation();
        this.speed.setQuantity(this.tile.getProgressScaled(26));
    }
}
