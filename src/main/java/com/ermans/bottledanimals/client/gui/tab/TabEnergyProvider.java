package com.ermans.bottledanimals.client.gui.tab;

import com.ermans.bottledanimals.IconRegistry;
import com.ermans.api.IEnergyInfoProvider;
import com.ermans.repackage.cofh.lib.gui.GuiBase;
import com.ermans.repackage.cofh.lib.gui.element.ElementIcon;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;

public class TabEnergyProvider extends TabEnergy{


    protected IEnergyInfoProvider tileEnergy;

    public TabEnergyProvider(GuiBase gui, IEnergyInfoProvider tileEnergy, int side) {
        super(gui, side);

        this.maxHeight = 85;

        this.tileEnergy = tileEnergy;

        addElement(new ElementIcon(gui, posXOffset() + 24, posY + 22, IconRegistry.getIcon("EnergyNow")).setTooltipText(Arrays.asList("Energy Generated")).setSize(16, 16));
        addElement(new ElementIcon(gui, posXOffset() + 24, posY + 36, IconRegistry.getIcon("EnergyOut")).setTooltipText(Arrays.asList("Energy Output")).setSize(16, 16));
        addElement(new ElementIcon(gui, posXOffset() + 25, posY + 50, IconRegistry.getIcon("EnergyMax")).setTooltipText(Arrays.asList("Maximum Energy Output")).setSize(16, 16));
        addElement(new ElementIcon(gui, posXOffset() + 25, posY + 64, IconRegistry.getIcon("EnergyTime")).setTooltipText(Arrays.asList("Remaining Fuel")).setSize(16, 16));

    }

    @Override
    protected boolean isActive() {
        return tileEnergy.isActive();
    }

    @Override
    protected void drawForeground() {
        super.drawForeground();

        if (!isFullyOpened()) {
            return;
        }

        getFontRenderer().drawStringWithShadow("Energy", posXOffset() + 20, this.posY + 6, this.headerColor);

        getFontRenderer().drawString("RF/t", posXOffset() + 47, posY + 27, this.textColor);
        getFontRenderer().drawString("RF/t", posXOffset() + 47, posY + 41, this.textColor);
        getFontRenderer().drawString("RF/t", posXOffset() + 47, posY + 55, this.textColor);
        getFontRenderer().drawString("%", posXOffset() + 47, posY + 69, this.textColor);

        drawString("" + tileEnergy.getInfoEnergyPerTick(), posXOffset() + 20, this.posY + 27);
        drawString("" + tileEnergy.getEnergyDrainPerTick(), posXOffset() + 20, this.posY + 41);
        drawString("" + tileEnergy.getInfoMaxEnergyPerTick(), posXOffset() + 20, this.posY + 55);
        drawString(tileEnergy.getInfoFuelPercentage() + "", posXOffset() + 20, this.posY + 69);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
