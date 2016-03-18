package com.ermans.bottledanimals.nei;


import codechicken.nei.api.INEIGuiAdapter;
import codechicken.nei.api.TaggedInventoryArea;
import com.ermans.repackage.cofh.lib.gui.GuiBase;
import com.ermans.repackage.cofh.lib.gui.element.TabBase;
import com.ermans.repackage.cofh.lib.util.Rectangle4i;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.util.List;

public class NEIGuiHandler extends INEIGuiAdapter {

    public static NEIGuiHandler instance = new NEIGuiHandler();

    @Override
    public List<TaggedInventoryArea> getInventoryAreas(GuiContainer gui) {
        return super.getInventoryAreas(gui);
    }

    @Override
    public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h) {

        if (gui instanceof GuiBase) {

            Rectangle4i bounds = new Rectangle4i(x, y, w, h);

            for (TabBase tab : ((GuiBase) gui).tabs) {
                if (tab.getBounds().intersects(bounds)) {
                    return true;
                }
            }
        }
        return false;
    }
}
