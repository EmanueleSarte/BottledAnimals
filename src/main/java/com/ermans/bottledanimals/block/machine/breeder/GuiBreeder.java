package com.ermans.bottledanimals.block.machine.breeder;

import com.ermans.bottledanimals.client.gui.GuiBaseAdv;
import com.ermans.bottledanimals.client.gui.tab.TabInfo;
import com.ermans.bottledanimals.reference.Textures;
import com.ermans.repackage.cofh.lib.gui.element.ElementDualScaled;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiBreeder extends GuiBaseAdv {

    private TileBreeder tile;
    private ElementDualScaled speed;

    public GuiBreeder(InventoryPlayer invPlayer, TileBreeder entity) {
        super(new ContainerBreeder(invPlayer, entity), Textures.Gui.BREEDER,entity);
        this.tile = entity;
    }

    @Override
    public void initGui() {
        super.initGui();

        addTab(new TabInfo(this, TabBase.LEFT, "Get a baby digitalized animals from two digitalized parent"));
        this.speed = ((ElementDualScaled) addElement(new ElementDualScaled(this, 82, 27).setMode(1).setSize(31, 16).setTexture(Textures.Gui.Element.PROGRESS_ARROW_HEART, 64, 16)));
    }

    @Override
    protected void updateElementInformation() {
        super.updateElementInformation();
        this.speed.setQuantity(tile.getPercentage(31));
    }
}
