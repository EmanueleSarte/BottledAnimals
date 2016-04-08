package com.ermans.bottledanimals.block.machine.animaldigitizer;

import com.ermans.bottledanimals.client.gui.GuiBaseAdv;
import com.ermans.bottledanimals.client.gui.tab.TabInfo;
import com.ermans.bottledanimals.reference.Textures;
import com.ermans.repackage.cofh.lib.gui.element.ElementDualScaled;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiAnimalDigitizer extends GuiBaseAdv{

    private TileAnimalDigitizer tile;
    private ElementDualScaled speed;

    public GuiAnimalDigitizer(InventoryPlayer invPlayer, TileAnimalDigitizer entity) {
        super(new ContainerAnimalDigitizer(invPlayer, entity), Textures.Gui.ANIMAL_DIGITIZER,entity);
        this.tile = entity;
    }

    @Override
    public void initGui() {
        super.initGui();
        addTab(new TabInfo(this, TabBase.LEFT, "Convert Bottled Animals to Digitalized Animals"));
        this.speed = ((ElementDualScaled) addElement(new ElementDualScaled(this, 80, 30).setMode(1).setSize(24, 16).setTexture(Textures.Gui.Element.PROGRESS_ARROW, 64, 16)));
    }

    @Override
    protected void updateElementInformation() {
        super.updateElementInformation();
        this.speed.setQuantity(tile.getPercentage(24));
    }
}
