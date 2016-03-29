package com.ermans.bottledanimals.client.gui.tab;


import com.ermans.api.IMachineInfo;
import com.ermans.repackage.cofh.lib.gui.GuiBase;
import org.lwjgl.opengl.GL11;

public class TabEnergyReceiver extends TabEnergy {

    protected IMachineInfo machineInfo;

    public TabEnergyReceiver(GuiBase gui, IMachineInfo tileEnergy, int side) {
        super(gui, side);

//        addElement(new ElementIcon(gui, posXOffset() + 24, posY + 22, IconRegistry.getIcon("EnergyNow")).setTooltipText(Arrays.asList("Energy currently in use")).setSize(16, 16));
//        addElement(new ElementIcon(gui, posXOffset() + 25, posY + 36, IconRegistry.getIcon("EnergyMax")).setTooltipText(Arrays.asList("Maximum energy input")).setSize(16, 16));
//        addElement(new ElementIcon(gui, posXOffset() + 25, posY + 50, IconRegistry.getIcon("EnergyTime")).setTooltipText(Arrays.asList("Operation time")).setSize(16, 16));

        machineInfo = tileEnergy;
    }

    @Override
    protected boolean isActive() {
        return machineInfo.isActive();
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
        getFontRenderer().drawString("%", posXOffset() + 47, posY + 55, this.textColor);

        drawString("" + machineInfo.getInfoEnergyPerTick(), posXOffset() + 20, this.posY + 27);
        drawString("" + machineInfo.getInfoMaxEnergyPerTick(), posXOffset() + 20, this.posY + 41);
        drawString(machineInfo.getInfoTimePercentage() + "", posXOffset() + 20, this.posY + 55);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
