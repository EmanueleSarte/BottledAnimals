package com.ermans.bottledanimals.client.gui.tab;


import com.ermans.bottledanimals.helper.ColorHelper;
import com.ermans.repackage.cofh.lib.gui.GuiBase;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import com.ermans.repackage.cofh.lib.util.helpers.StringHelper;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class TabInfo extends TabBase {

    private List<String> lines;

    public TabInfo(GuiBase gui, int side, String text) {
        super(gui, side);

        this.headerColor = 14797103;
        this.subheaderColor = 11186104;
        this.textColor = 0;
        this.backgroundColor = ColorHelper.rgb(191, 85, 236);

        setText(text);

        this.maxHeight = this.posY + 12 + 10 * lines.size() + 20;
        this.maxWidth = 100;


    }

    public void setText(String text) {
        this.lines = StringHelper.getLinesFromString(17, text);
    }

    @Override
    protected void drawForeground() {
        drawTabIcon("IconMachineInfo");
        if (!isFullyOpened()) {
            return;
        }

        getFontRenderer().drawStringWithShadow("Info Tab", posXOffset() + 20, this.posY + 6, this.headerColor);
        for (int i = 0; i < lines.size(); i++) {
            getFontRenderer().drawString(lines.get(i), posXOffset() + 2, this.posY + 12 + 10 * (i + 1), this.textColor);
        }


        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void addTooltip(List<String> list) {
        super.addTooltip(list);
        if (!isFullyOpened()) {
            list.add("Machine Info");
        }
    }
}
