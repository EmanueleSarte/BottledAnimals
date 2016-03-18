package com.ermans.bottledanimals.client.gui.tab;

import com.ermans.bottledanimals.IconRegistry;
import com.ermans.bottledanimals.block.IEnergyInfoReceiver;
import com.ermans.bottledanimals.helper.ColorHelper;
import com.ermans.repackage.cofh.lib.gui.GuiBase;
import com.ermans.repackage.cofh.lib.gui.element.ElementIcon;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import com.ermans.repackage.cofh.lib.util.helpers.StringHelper;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.List;

public class TabEnergy extends TabBase {

    private IEnergyInfoReceiver tileEnergy;


    public TabEnergy(GuiBase gui, IEnergyInfoReceiver tileEnergy, int side) {
        super(gui, side);

        this.tileEnergy = tileEnergy;

        this.headerColor = 14797103;
        this.subheaderColor = 11186104;
        this.textColor = 0;
        this.backgroundColor = ColorHelper.rgb(34, 167, 240);

        this.maxHeight = 67;
        this.maxWidth = 80;

        addElement(new ElementIcon(gui, posXOffset() + 24, posY + 20, IconRegistry.getIcon("EnergyNow")).setTooltipText(Arrays.asList("Energy currently in use")).setSize(16, 16));
        addElement(new ElementIcon(gui, posXOffset() + 25, posY + 32, IconRegistry.getIcon("EnergyMax")).setTooltipText(Arrays.asList("Maximum energy input")).setSize(16, 16));
        addElement(new ElementIcon(gui, posXOffset() + 25, posY + 44, IconRegistry.getIcon("EnergyTime")).setTooltipText(Arrays.asList("Operation time")).setSize(16, 16));

    }


    @Override
    protected void drawForeground() {

        if (tileEnergy.getInfoEnergyPerTick() > 0) {
            drawTabIcon("IconEnergyOn");
        } else {
            drawTabIcon("IconEnergy");
        }
        if (!isFullyOpened()) {
            return;
        }

        getFontRenderer().drawStringWithShadow("Energy", posXOffset() + 20, this.posY + 6, this.headerColor);

        getFontRenderer().drawString("RF/t", posXOffset() + 47, posY + 25, this.textColor);
        getFontRenderer().drawString("RF/t", posXOffset() + 47, posY + 37, this.textColor);
        getFontRenderer().drawString("%", posXOffset() + 47, posY + 49, this.textColor);

        drawString("" + tileEnergy.getInfoEnergyPerTick(), posXOffset() + 20, this.posY + 25);
        drawString("" + tileEnergy.getInfoMaxReceiveEnergyPerTick(), posXOffset() + 20, this.posY + 37);
        drawString(tileEnergy.getInfoTimePercentage() + "", posXOffset() + 20, this.posY + 49);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }


    protected void drawString(String text, int x, int y) {
        int offset;
        switch (text.length()) {
            case 1:
                offset = 19;
                break;
            case 2:
                offset = 13;
                break;
            case 3:
                offset = 7;
                break;
            case 4:
                offset = 1;
                break;
            default:
                offset = 0;
                break;
        }
        getFontRenderer().drawString(text, x + offset, y, this.textColor);
    }

    @Override
    public void addTooltip(List<String> tooltipList) {
        super.addTooltip(tooltipList);
        if (!isFullyOpened()) {
            tooltipList.add("Energy");
            if (tileEnergy.getInfoEnergyPerTick() > 0) {
                tooltipList.add(StringHelper.BRIGHT_GREEN + StringHelper.ITALIC + "  ON" + StringHelper.END);
            }
        }
    }


}

