package com.ermans.bottledanimals.block.generator.basicgenerator;

import com.ermans.bottledanimals.client.gui.GuiBaseAdv;
import com.ermans.bottledanimals.client.gui.tab.TabInfo;
import com.ermans.bottledanimals.reference.Textures;
import com.ermans.repackage.cofh.lib.gui.element.ElementDualScaled;
import com.ermans.repackage.cofh.lib.gui.element.ElementEnergyStored;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiBasicGenerator extends GuiBaseAdv{

    private TileBasicGenerator tile;
    private ElementDualScaled speed;

    public GuiBasicGenerator(InventoryPlayer invPlayer, TileBasicGenerator entity) {
        super(new ContainerBasicGenerator(invPlayer, entity), Textures.Gui.BASIC_GENERATOR,entity);
        this.tile = entity;
    }

    @Override
    public void initGui() {
        super.initGui();
        addTab(new TabInfo(this, TabBase.LEFT, "It generates power from solid fuel"));
        ElementEnergyStored elementEnergyStored = new ElementEnergyStored(this, tile.getEnergyStorage());
        elementEnergyStored.setPosition(100,21);
        addElement(elementEnergyStored);
        this.speed = ((ElementDualScaled) addElement(new ElementDualScaled(this, 58, 45).setMode(1).setSize(16, 16).setTexture(Textures.Gui.Element.PROGRESS_FIRE, 32, 16)));
    }

    @Override
    protected void updateElementInformation() {
        super.updateElementInformation();
        this.speed.setQuantity(this.tile.getFuelScaled(16));
    }
}
