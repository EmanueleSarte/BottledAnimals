package com.ermans.bottledanimals.block.machine.rancher;

import com.ermans.bottledanimals.client.gui.GuiBaseAdv;
import com.ermans.bottledanimals.client.gui.tab.TabInfo;
import com.ermans.bottledanimals.reference.Textures;
import com.ermans.repackage.cofh.lib.gui.element.ElementDualScaled;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiRancher extends GuiBaseAdv {


    private TileRancher tile;
    private ElementDualScaled speedShears;
    private ElementDualScaled speedBucket;

    public GuiRancher(InventoryPlayer invPlayer, TileRancher entity) {
        super(new ContainerRancher(invPlayer, entity), Textures.Gui.RANCHER, entity);
        this.tile = entity;
    }

    @Override
    public void initGui() {
        super.initGui();
        addTab(new TabInfo(this, TabBase.LEFT, "Ranch the animal to get its products"));

        this.speedShears = ((ElementDualScaled) addElement(new ElementDualScaled(this, 69, 40).setMode(0).setSize(16, 12).setTexture(Textures.Gui.Element.PROGRESS_SHEARS, 32, 16)));
        this.speedBucket = ((ElementDualScaled) addElement(new ElementDualScaled(this, 69, 40).setMode(0).setSize(16, 13).setTexture(Textures.Gui.Element.PROGRESS_MILK, 32, 16)));
        this.speedBucket.setVisible(false);
    }

    @Override
    protected void updateElementInformation() {
        switch (tile.getProcessingAnimal()) {
            case 2:
            case 6:
                if (!speedBucket.isVisible()) {
                    speedShears.setVisible(false);
                    speedBucket.setVisible(true);
                }
                speedBucket.setQuantity(this.tile.getPercentage(13));
                break;
            default:
                if (!speedShears.isVisible()) {
                    speedShears.setVisible(true);
                    speedBucket.setVisible(false);
                }
                speedShears.setQuantity(this.tile.getPercentage(12));
        }

    }
}
