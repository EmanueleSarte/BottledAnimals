package com.ermans.bottledanimals.client.gui.tab;

import com.ermans.bottledanimals.helper.ColorHelper;
import com.ermans.repackage.cofh.lib.gui.GuiBase;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import com.ermans.repackage.cofh.lib.util.helpers.StringHelper;

import java.util.List;

public abstract class TabEnergy extends TabBase {


    public TabEnergy(GuiBase gui, int side) {
        super(gui, side);

        this.headerColor = 14797103;
        this.subheaderColor = 11186104;
        this.textColor = 0;
        this.backgroundColor = ColorHelper.rgb(34, 167, 240);

        this.maxHeight = 67;
        this.maxWidth = 80;

    }


    @Override
    protected void drawForeground() {

//        if (isActive()) {
//            drawTabIcon("IconEnergyOn");
//        } else {
//            drawTabIcon("IconEnergy");
//        }
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

    protected boolean isActive(){
        return false;
    }

    @Override
    public void addTooltip(List<String> tooltipList) {
        super.addTooltip(tooltipList);
        if (!isFullyOpened()) {
            tooltipList.add("Energy");
            if (isActive()) {
                tooltipList.add(StringHelper.BRIGHT_GREEN + StringHelper.ITALIC + "  ON" + StringHelper.END);
            }
        }
    }


}

