package com.ermans.bottledanimals.block.machine.animalmaterializer;


import com.ermans.bottledanimals.client.gui.GuiBaseAdv;
import com.ermans.bottledanimals.client.gui.tab.TabInfo;
import com.ermans.bottledanimals.reference.Textures;
import com.ermans.repackage.cofh.lib.gui.element.ElementDualScaled;
import com.ermans.repackage.cofh.lib.gui.element.ElementEnergyStored;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiAnimalMaterializer extends GuiBaseAdv{

    private TileAnimalMaterializer tile;
    private ElementDualScaled speed;

    public GuiAnimalMaterializer(InventoryPlayer invPlayer, TileAnimalMaterializer entity) {
        super(new ContainerAnimalMaterializer(invPlayer, entity), Textures.Gui.ANIMAL_MATERIALIZER,entity);
        this.tile = entity;
    }

    @Override
    public void initGui() {
        super.initGui();
        addTab(new TabInfo(this, TabBase.LEFT, "Materializes digitalized Animals into Spawn Eggs"));
        addElement(new ElementEnergyStored(this, tile.getEnergyStorage()).setAlwaysShow(true));
        this.speed = ((ElementDualScaled) addElement(new ElementDualScaled(this, 97, 36).setMode(1).setSize(24, 16).setTexture(Textures.Gui.Element.PROGRESS_ARROW, 64, 16)));
    }

    @Override
    protected void updateElementInformation() {
        super.updateElementInformation();
        this.speed.setQuantity(this.tile.getProgressScaled(24));
    }
}
