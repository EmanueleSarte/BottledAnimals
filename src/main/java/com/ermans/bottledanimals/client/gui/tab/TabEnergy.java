package com.ermans.bottledanimals.client.gui.tab;

import com.ermans.bottledanimals.IconRegistry;
import com.ermans.bottledanimals.api.ITileInfo;
import com.ermans.bottledanimals.helper.ColorHelper;
import com.ermans.repackage.cofh.lib.gui.GuiBase;
import com.ermans.repackage.cofh.lib.gui.element.ElementIcon;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import com.ermans.repackage.cofh.lib.util.helpers.StringHelper;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.List;

public class TabEnergy extends TabBase {

    protected ITileInfo tileInfo;

    public TabEnergy(GuiBase gui,ITileInfo tileInfo, int side) {
        super(gui, side);

        this.headerColor = 14797103;
        this.subheaderColor = 11186104;
        this.textColor = 0;
        this.backgroundColor = ColorHelper.rgb(34, 167, 240);

        this.maxWidth = 80;
        this.maxHeight = 85;

        if (tileInfo.isReceiver()) {
            addElement(new ElementIcon(gui, posXOffset() + 24, posY + 22, IconRegistry.getIcon("EnergyNow")).setTooltipText(Arrays.asList("Energy currently consumed")).setSize(16, 16));
            addElement(new ElementIcon(gui, posXOffset() + 24, posY + 36, IconRegistry.getIcon("EnergyOut")).setTooltipText(Arrays.asList("Energy Input")).setSize(16, 16));
            addElement(new ElementIcon(gui, posXOffset() + 25, posY + 50, IconRegistry.getIcon("EnergyTime")).setTooltipText(Arrays.asList("Operation time")).setSize(16, 16));
            addElement(new ElementIcon(gui, posXOffset() + 25, posY + 64, IconRegistry.getIcon("EnergyMax")).setTooltipText(Arrays.asList("Maximum Energy Input")).setSize(16, 16));
        }else{
            addElement(new ElementIcon(gui, posXOffset() + 24, posY + 22, IconRegistry.getIcon("EnergyNow")).setTooltipText(Arrays.asList("Energy currently generated")).setSize(16, 16));
            addElement(new ElementIcon(gui, posXOffset() + 24, posY + 36, IconRegistry.getIcon("EnergyOut")).setTooltipText(Arrays.asList("Energy Output")).setSize(16, 16));
            addElement(new ElementIcon(gui, posXOffset() + 25, posY + 50, IconRegistry.getIcon("EnergyTime")).setTooltipText(Arrays.asList("Remaining Fuel")).setSize(16, 16));
            addElement(new ElementIcon(gui, posXOffset() + 25, posY + 64, IconRegistry.getIcon("EnergyMax")).setTooltipText(Arrays.asList("Maximum Energy Output")).setSize(16, 16));
        }

        this.tileInfo = tileInfo;

    }


    @Override
    protected void drawForeground() {

        if (tileInfo.isTileActive()) {
            drawTabIcon("IconEnergyOn");
        } else {
            drawTabIcon("IconEnergy");
        }

        if (!isFullyOpened()) {
            return;
        }

        getFontRenderer().drawStringWithShadow("Energy", posXOffset() + 20, this.posY + 6, this.headerColor);


        getFontRenderer().drawString("RF/t", posXOffset() + 47, posY + 27, this.textColor);
        getFontRenderer().drawString("RF/t", posXOffset() + 47, posY + 41, this.textColor);
        getFontRenderer().drawString("%", posXOffset() + 47, posY + 55, this.textColor);
        getFontRenderer().drawString("RF/t", posXOffset() + 47, posY + 69, this.textColor);


        drawString("" + tileInfo.getActualEnergyPerTick(), posXOffset() + 20, this.posY + 27);
        drawString("" + tileInfo.getEnergyPerTick(), posXOffset() + 20, this.posY + 41);
        drawString("" + tileInfo.getPercentage(100), posXOffset() + 20, this.posY + 55);
        drawString("" + tileInfo.getEnergyMaxPerTick(), posXOffset() + 20, this.posY + 69);

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
            if (tileInfo.isTileActive()) {
                tooltipList.add(StringHelper.BRIGHT_GREEN + StringHelper.ITALIC + "  ON" + StringHelper.END);
            }
        }
    }


}

